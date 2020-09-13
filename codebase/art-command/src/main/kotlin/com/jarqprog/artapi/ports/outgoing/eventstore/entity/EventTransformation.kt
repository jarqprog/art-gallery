package com.jarqprog.artapi.ports.outgoing.eventstore.entity

import com.jarqprog.artapi.ports.outgoing.MAPPER
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.events.ResourceChanged
import com.jarqprog.artapi.ports.outgoing.eventstore.exceptions.EventStoreFailure
import java.util.*
import java.util.function.Function

class ToDescriptor : Function<ArtEvent, EventDescriptor> {

    override fun apply(event: ArtEvent): EventDescriptor {
        return EventDescriptor(
                UUID.randomUUID(),
                event.artId().uuid(),
                event.version(),
                event.timestamp(),
                event.type(),
                event.name(),
                MAPPER.writeValueAsString(event)
        )
    }
}

class ToEvent : Function<EventDescriptor, ArtEvent> {

    override fun apply(descriptor: EventDescriptor): ArtEvent {
        val payload = descriptor.payload
        return when (descriptor.name) {
            ArtCreated::class.java.simpleName -> MAPPER.readValue(payload, ArtCreated::class.java)
            ResourceChanged::class.java.simpleName -> MAPPER.readValue(payload, ResourceChanged::class.java)
            else -> raiseFailure(descriptor)
        }
    }

    private fun raiseFailure(descriptor: EventDescriptor): Nothing {
        throw EventStoreFailure("error while retrieving data for $descriptor")
    }
}
