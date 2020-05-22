package com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.sql

import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStreamDatabase
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ArtHistoryDescriptor
import org.slf4j.LoggerFactory
import java.util.*

class EventStreamJDBC(private val streamRepository: HistoryRepository) : EventStreamDatabase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun historyExistsById(artId: Identifier): Boolean {
        return streamRepository.existsById(artId.value)
    }

    override fun streamVersion(artId: Identifier): Optional<Int> {
        return streamRepository.findVersionByArtId(artId.value)
    }

    override fun save(eventStream: ArtHistoryDescriptor) {
        streamRepository.save(eventStream)
    }

    override fun load(artId: Identifier): Optional<ArtHistoryDescriptor> {
        return streamRepository.findById(artId.value)
    }
}