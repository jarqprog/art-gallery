package com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.sql

import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStreamDatabase
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ArtHistoryDescriptor
import org.slf4j.LoggerFactory
import java.util.*

class EventStreamJDBC(private val artStreamRepository: ArtStreamRepository) : EventStreamDatabase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun historyExistsById(artId: Identifier): Boolean {
        return artStreamRepository.existsById(artId.value)
    }

    override fun streamVersion(artId: Identifier): Optional<Int> {
        return artStreamRepository.findVersionByArtId(artId.value)
    }

    override fun save(eventStream: ArtHistoryDescriptor) {
        artStreamRepository.save(eventStream)
    }

    override fun load(artId: Identifier): Optional<ArtHistoryDescriptor> {
        return artStreamRepository.findById(artId.value)
    }
}