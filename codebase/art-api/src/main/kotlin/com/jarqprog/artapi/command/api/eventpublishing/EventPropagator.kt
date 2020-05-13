package com.jarqprog.artapi.command.api.eventpublishing

import com.jarqprog.artapi.command.api.EventPublishing
import com.jarqprog.artapi.command.ports.outgoing.EventStore
import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.api.exceptions.EventProcessingFailure
import java.util.*

class EventPropagator(private val eventStorage: EventStore) : EventPublishing {

    override fun publish(event: ArtEvent): Optional<EventProcessingFailure> {
        return eventStorage.save(event)
                .map { failure -> EventProcessingFailure.fromThrowable(failure) }
    }
}