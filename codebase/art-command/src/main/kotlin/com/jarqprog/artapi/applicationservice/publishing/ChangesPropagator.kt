package com.jarqprog.artapi.applicationservice.publishing

import com.jarqprog.artapi.domain.art.ProcessingResult
import com.jarqprog.artapi.domain.ChangesPublishing
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStore
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

class ChangesPropagator(private val eventStorage: EventStore) : ChangesPublishing {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun publish(processingResult: ProcessingResult): Mono<Void> {
        return eventStorage.save(processingResult)
    }

    //todo
    private fun handleProjectionUpdateFailure(event: ArtEvent, failure: Throwable) {
        logger.error("Error on updating projection for event: $event", failure)
    }
}
