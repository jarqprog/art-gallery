package com.jarqprog.artapi.command.infrastructure.eventstore

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.*
import com.jarqprog.artapi.command.artdomain.events.ArtCreated
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.events.ResourceChanged
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.EventDescriptor
import com.jarqprog.artapi.command.infrastructure.eventstore.exceptions.EventStoreFailure
import java.util.*
import java.util.function.Function

private val mapper = jacksonObjectMapper()
        .registerModule(JavaTimeModule())
        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)

class EventToDescriptor : Function<ArtEvent, EventDescriptor> {

    override fun apply(event: ArtEvent): EventDescriptor {
        return EventDescriptor(
                UUID.randomUUID(),
                event.artId().value,
                event.version(),
                event.timestamp(),
                event.eventType(),
                event.eventName(),
                mapper.writeValueAsString(event)
        )
    }
}

class DescriptorToEvent : Function<EventDescriptor, ArtEvent> {

    override fun apply(descriptor: EventDescriptor): ArtEvent {
        val payload = descriptor.payload
        return when (descriptor.eventName) {
            ArtCreated::class.java.simpleName -> mapper.readValue(payload, ArtCreated::class.java)
            ResourceChanged::class.java.simpleName -> mapper.readValue(payload, ResourceChanged::class.java)
            else -> raiseFailure(descriptor)
        }
    }

    private fun raiseFailure(descriptor: EventDescriptor): Nothing {
        throw EventStoreFailure(
                "error while retrieving data for ${descriptor.eventName}" +
                        " uuid:${descriptor.artId} version:${descriptor.version} timestamp:${descriptor.timestamp}")
    }
}
