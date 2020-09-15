package com.jarqprog.artapi.domain.art

import com.jarqprog.artapi.support.EventContainer.ANY_IDENTIFIER
import com.jarqprog.artapi.support.HistoryContainer.HISTORY_WITH_THREE_EVENTS

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

internal class ArtTest {

    @Test
    fun `should create initial state`() {
        //given
        val expectedIdentifier = ANY_IDENTIFIER
        val expectedVersion = -1
        val expectedTimestamp = Instant.MIN

        //when
        val initialState = ArtAggregate.initialState(ANY_IDENTIFIER)

        //then
        assertThat(initialState.identifier()).isEqualTo(expectedIdentifier)
        assertThat(initialState.version()).isEqualTo(expectedVersion)
        assertThat(initialState.timestamp()).isEqualTo(expectedTimestamp)
    }

    @Test
    fun `should create descriptor`() {
        //given
        val art = ArtAggregate.replayAll(HISTORY_WITH_THREE_EVENTS)

        //when
        val descriptor = art.asDescriptor()

        //then
        assertThat(descriptor.artId).isEqualTo(art.identifier().uuid())
        assertThat(descriptor.version).isEqualTo(art.version())
        assertThat(descriptor.timestamp).isEqualTo(art.timestamp())
        assertThat(descriptor.payload).isEqualTo(art.asJson())
    }
}