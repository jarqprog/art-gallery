package com.jarqprog.artapi.command.ports.outgoing.eventstore.inmemory

import com.jarqprog.artapi.command.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStreamDatabase
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ArtHistoryDescriptor
import org.slf4j.LoggerFactory

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryEventStreamDatabase(private val memory: ConcurrentHashMap<String, ArtHistoryDescriptor>) : EventStreamDatabase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun historyExistsById(artId: Identifier): Boolean {
        return memory[artId.value] != null
    }

    override fun streamVersion(artId: Identifier): Optional<Int> {
        return Optional.ofNullable(memory[artId.value])
                .map(ArtHistoryDescriptor::version)
    }

    override fun save(eventStream: ArtHistoryDescriptor) {
        logger.info("Saving event stream {} to memory {}", eventStream, memory)
        memory[eventStream.artId] = eventStream
    }

    override fun load(artId: Identifier): Optional<ArtHistoryDescriptor> = Optional.ofNullable(memory[artId.value])
}
