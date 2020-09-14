package com.jarqprog.artapi.ports.outgoing.eventstore.dao.inmemory

import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStreamDatabase
import com.jarqprog.artapi.ports.outgoing.eventstore.entity.EventDescriptor
import reactor.core.publisher.Mono
import java.util.*

import java.util.concurrent.ConcurrentHashMap

class InMemoryEventStreamDatabase(private val memory: ConcurrentHashMap<UUID, MutableList<EventDescriptor>>)
    : EventStreamDatabase {

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
