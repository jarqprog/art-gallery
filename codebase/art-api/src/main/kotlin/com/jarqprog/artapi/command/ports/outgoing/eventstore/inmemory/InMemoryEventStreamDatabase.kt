package com.jarqprog.artapi.command.ports.outgoing.eventstore.inmemory

import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStreamDatabase
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ArtHistoryDescriptor

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryEventStreamDatabase(private val memory: ConcurrentHashMap<String, ArtHistoryDescriptor>) : EventStreamDatabase {

    override fun historyExistsById(artId: Identifier): Boolean {
        return memory[artId.value] != null
    }

    override fun streamVersion(artEvent: ArtEvent): Optional<Int> {
        return Optional.ofNullable(memory[artEvent.artId().value])
                .map(ArtHistoryDescriptor::version)
    }

    override fun save(eventStream: ArtHistoryDescriptor) {
        memory[eventStream.artId] = eventStream
    }

    override fun load(artId: Identifier): Optional<ArtHistoryDescriptor> = Optional.ofNullable(memory[artId.value])
}
