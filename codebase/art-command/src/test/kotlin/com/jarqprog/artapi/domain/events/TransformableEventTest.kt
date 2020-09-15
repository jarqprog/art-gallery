package com.jarqprog.artapi.domain.events

import com.jarqprog.artapi.support.EventContainer.EVENT_ART_CREATED
import com.jarqprog.artapi.domain.exceptions.EventProcessingFailure
import com.jarqprog.artapi.support.SerializationHelper
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.util.*

class TransformableEventTest {

    @Test
    fun `should create descriptor from event`() {
        //given
        val event = EVENT_ART_CREATED

        //when
        val descriptor = event.asDescriptor()

        //then
        assertThat(descriptor.artId).isEqualTo(event.artId().uuid())
        assertThat(descriptor.timestamp).isEqualTo(event.timestamp())
        assertThat(descriptor.type).isEqualTo(event.type())
        assertThat(descriptor.name).isEqualTo(event.name())
        assertThat(descriptor.payload).isEqualTo(SerializationHelper.toJson(event))
    }

    @Test
    fun `should recreate event from descriptor`() {
        //given
        val event = EVENT_ART_CREATED
        val descriptor = EventDescriptor(
                UUID.randomUUID(),
                event.artId().uuid(),
                event.version(),
                event.timestamp(),
                event::class.java.superclass.simpleName,
                event::class.java.simpleName,
                SerializationHelper.toJson(EVENT_ART_CREATED)
        )

        //when
        val recreated = TransformableEvent.fromDescriptor(descriptor)

        //then
        assertThat(recreated).isEqualTo(event)
    }

    @Test
    fun `should throw processing event exception on recreation event on invalid descriptor name`() {
        //given
        val invalidDescriptorName = "non-handled-name"
        val descriptor = EventDescriptor(
                UUID.randomUUID(),
                UUID.randomUUID(),
                0,
                Instant.now(),
                ArtEvent::class.java.simpleName,
                invalidDescriptorName,
                SerializationHelper.toJson(EVENT_ART_CREATED)
        )

        //when then
        assertThrows<EventProcessingFailure> { TransformableEvent.fromDescriptor(descriptor) }
    }

    @Test
    fun `should throw processing event exception on recreation event on invalid descriptor payload`() {
        //given
        val invalidPayload = "invalid-payload"
        val descriptor = EventDescriptor(
                UUID.randomUUID(),
                UUID.randomUUID(),
                0,
                Instant.now(),
                ArtEvent::class.java.simpleName,
                ArtCreated::class.java.simpleName,
                invalidPayload
        )

        //when then
        assertThrows<EventProcessingFailure> { TransformableEvent.fromDescriptor(descriptor) }
    }

}