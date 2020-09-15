package com.jarqprog.artapi.support

import com.jarqprog.artapi.domain.events.EventDescriptor
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStreamRepository
import reactor.core.publisher.Mono
import java.util.*

import java.util.concurrent.ConcurrentHashMap

class InMemoryEventStreamRepository(private val memory: ConcurrentHashMap<UUID, MutableList<EventDescriptor>>)
    : EventStreamRepository {

    override fun historyExistsById(artId: Identifier): Mono<Boolean> = Mono
            .just(memory[artId.uuid()] != null)

    override fun streamVersion(artId: Identifier): Mono<Int> {
        return Mono.fromCallable<MutableList<EventDescriptor>> { memory[artId.uuid()] }
                .map { events -> events.last() }
                .map(EventDescriptor::version)
    }

    override fun save(event: EventDescriptor): Mono<Void> = Mono
            .fromCallable<MutableList<EventDescriptor>> { memory[event.artId] }
            .switchIfEmpty(
                    Mono.just(mutableListOf<EventDescriptor>())
                            .map { events ->
                                memory[event.artId] = events
                                events
                            }
            )
            .map { events -> events.add(event) }
            .then()

    override fun load(artId: Identifier): Mono<List<EventDescriptor>> = Mono.fromCallable { memory[artId.uuid()] }
}
