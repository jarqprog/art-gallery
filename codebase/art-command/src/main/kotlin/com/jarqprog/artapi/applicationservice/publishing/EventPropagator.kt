package com.jarqprog.artapi.applicationservice.publishing

import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStore
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

class EventPropagator(
        private val eventStorage: EventStore
) : EventPublishing {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun publish(event: ArtEvent): Mono<Void> {
        return eventStorage.save(event)
    }

    private fun handleProjectionUpdateFailure(event: ArtEvent, failure: Throwable) {
        logger.error("Error on updating projection for event: $event", failure)
    }
}