package com.jarqprog.artapi.command.ports.outgoing.eventstore.sql

import com.jarqprog.artapi.command.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStreamDatabase
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ArtHistoryDescriptor
import org.slf4j.LoggerFactory
import java.util.*

class EventStreamJDBC(private val artStreamRepository: ArtStreamRepository): EventStreamDatabase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun historyExistsById(artId: Identifier): Boolean {
        logger.info("Searching if event stream exists by art identifier: {}", artId)
        return artStreamRepository.existsById(artId.value)
    }

    override fun streamVersion(artId: Identifier): Optional<Int> {
        logger.info("Searching for event stream version by art identifier: {}", artId)
        return artStreamRepository.findVersionByArtId(artId.value)
    }

    override fun save(eventStream: ArtHistoryDescriptor) {
        logger.info("Saving event stream: {}", eventStream)
        artStreamRepository.save(eventStream)
    }

    override fun load(artId: Identifier): Optional<ArtHistoryDescriptor> {
        logger.info("loading event stream by art identifier: {}", artId)
        return artStreamRepository.findById(artId.value)
    }
}