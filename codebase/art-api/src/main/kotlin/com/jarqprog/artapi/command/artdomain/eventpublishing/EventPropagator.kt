package com.jarqprog.artapi.command.artdomain.eventpublishing

import com.jarqprog.artapi.command.artdomain.EventPublishing
import com.jarqprog.artapi.command.artdomain.EventStore
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.exceptions.EventProcessingFailure
import java.util.*

class EventPropagator(private val artEventStorage: EventStore) : EventPublishing {

    override fun publish(event: ArtEvent): Optional<EventProcessingFailure> {
        return artEventStorage.save(event)
                .map{ failure -> EventProcessingFailure.fromThrowable(failure) }
    }
}