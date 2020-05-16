package com.jarqprog.artapi.command.api.eventpublishing

import com.jarqprog.artapi.command.api.EventPublishing
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.command.api.exceptions.EventProcessingFailure
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStore
import com.jarqprog.artapi.command.ports.outgoing.projection.ProjectionHandling
import org.slf4j.LoggerFactory
import java.util.*

class EventPropagator(
        private val eventStorage: EventStore,
        private val projectionHandling: ProjectionHandling
) : EventPublishing {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun publish(event: ArtEvent): Optional<EventProcessingFailure> {
        return eventStorage.save(event)
                .let { optionalFailure ->
                    optionalFailure
                            .map { failure -> EventProcessingFailure.fromThrowable(failure) }
                            .or {
                                projectionHandling.handle(event.artId())
                                        .map { failure -> handleProjectionUpdateFailure(event, failure) }
                                Optional.empty()
                            }
                }
    }

    private fun handleProjectionUpdateFailure(event: ArtEvent, failure: Throwable) {
        logger.error("Error on updating projection for event: $event", failure)
    }
}