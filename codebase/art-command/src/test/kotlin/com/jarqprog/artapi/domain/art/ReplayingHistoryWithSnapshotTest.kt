package com.jarqprog.artapi.domain.art

import com.jarqprog.artapi.domain.art.ArtTestUtils.eventsToDescriptors
import com.jarqprog.artapi.support.ArtAggregateContainer.EXPECTED_STATE_VERSION_2
import com.jarqprog.artapi.support.EventContainer.ANY_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.THREE_EVENTS_LIST
import com.jarqprog.artapi.support.EventContainer.TWO_EVENT_LIST

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ReplayingHistoryWithSnapshotTest {

    @Test
    fun `should return initial state when replaying empty history`() {
        //given
        val emptyHistory = ArtHistory.with(ANY_IDENTIFIER)

        //when
        val result = ArtAggregate.replayAll(emptyHistory)

        //then
        assertThat(result).isEqualTo(ArtAggregate.initialState(ANY_IDENTIFIER))
    }

    @Test
    fun `should replay state when history contain events`() {
        //given
        val historyV2 = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(THREE_EVENTS_LIST))

        //when
        val result = ArtAggregate.replayAll(historyV2)

        //then
        assertThat(EXPECTED_STATE_VERSION_2.isEqualTo(result)).isTrue()
    }

    @Test
    fun `should replay state from snapshot and events`() {
        //given
        val historyV1 = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(TWO_EVENT_LIST))
        val snapshotV1 = ArtAggregate.replayAll(historyV1)
        val historyV2 = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(THREE_EVENTS_LIST), snapshotV1.asDescriptor())

        //when
        val result = ArtAggregate.replayAll(historyV2)

        //then
        assertThat(EXPECTED_STATE_VERSION_2.isEqualTo(result)).isTrue()
    }
}