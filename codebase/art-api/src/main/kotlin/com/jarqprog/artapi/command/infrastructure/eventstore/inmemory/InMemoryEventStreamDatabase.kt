package com.jarqprog.artapi.command.infrastructure.eventstore.inmemory

import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.infrastructure.eventstore.EventStreamDatabase
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.EventStream

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryEventStreamDatabase(private val memory: ConcurrentHashMap<String, EventStream>) : EventStreamDatabase {

    override fun historyExistsById(artId: Identifier): Boolean {
        return memory[artId.value] != null
    }

    override fun streamVersion(artEvent: ArtEvent): Optional<Int> {
        return Optional.ofNullable(memory[artEvent.artId().value])
                .map(EventStream::version)
    }

    override fun save(eventStream: EventStream) {
        memory[eventStream.artId] = eventStream
    }

    override fun load(artId: Identifier): Optional<EventStream> = Optional.ofNullable(memory[artId.value])
}
