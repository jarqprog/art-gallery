package com.jarqprog.artapi.domain.events

import com.jarqprog.artapi.support.EventContainer.EVENT_ART_CREATED
import com.jarqprog.artapi.support.SerializationHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.*

class EventDescriptorTest {

    @Test
    fun `should properly compare time`() {
        //given
        val creationTime = Instant.now()
        val descriptor = EventDescriptor(
                UUID.randomUUID(),
                UUID.randomUUID(),
                0,
                creationTime,
                ArtEvent::class.java.simpleName,
                ArtCreated::class.java.simpleName,
                SerializationHelper.toJson(EVENT_ART_CREATED)
        )

        //when
        val tenSecondsLater = creationTime.plusSeconds(10)
        val result = descriptor.isNotLaterThan(tenSecondsLater)

        //then
        assertThat(result).isTrue()
    }
}
