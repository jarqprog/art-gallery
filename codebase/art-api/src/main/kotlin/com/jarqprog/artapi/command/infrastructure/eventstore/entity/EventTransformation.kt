package com.jarqprog.artapi.command.infrastructure.eventstore.entity

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jarqprog.artapi.command.artdomain.events.ArtCreated
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.events.ResourceChanged
import com.jarqprog.artapi.command.infrastructure.eventstore.exceptions.EventStoreFailure
import java.util.*
import java.util.function.Function

private val MAPPER = jacksonObjectMapper()
        .registerModule(JavaTimeModule())
        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)

class EventToDescriptor : Function<ArtEvent, ArtEventDescriptor> {

    override fun apply(event: ArtEvent): ArtEventDescriptor {
        return ArtEventDescriptor(
                UUID.randomUUID(),
                event.artId().value,
                event.version(),
                event.timestamp(),
                event.eventType(),
                event.eventName(),
                MAPPER.writeValueAsString(event)
        )
    }
}

class DescriptorToEvent : Function<ArtEventDescriptor, ArtEvent> {

    override fun apply(descriptor: ArtEventDescriptor): ArtEvent {
        val payload = descriptor.payload
        return when (descriptor.eventName) {
            ArtCreated::class.java.simpleName -> MAPPER.readValue(payload, ArtCreated::class.java)
            ResourceChanged::class.java.simpleName -> MAPPER.readValue(payload, ResourceChanged::class.java)
            else -> raiseFailure(descriptor)
        }
    }

    private fun raiseFailure(descriptor: ArtEventDescriptor): Nothing {
        throw EventStoreFailure(
                "error while retrieving data for ${descriptor.eventName}" +
                        " uuid:${descriptor.artId} version:${descriptor.version} timestamp:${descriptor.timestamp}")
    }
}
