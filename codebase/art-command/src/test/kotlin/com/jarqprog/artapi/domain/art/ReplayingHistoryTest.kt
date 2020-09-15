package com.jarqprog.artapi.domain.art

import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.support.ArtAggregateContainer.EXPECTED_STATE_VERSION_0
import com.jarqprog.artapi.support.ArtAggregateContainer.EXPECTED_STATE_VERSION_1
import com.jarqprog.artapi.support.ArtAggregateContainer.EXPECTED_STATE_VERSION_2
import com.jarqprog.artapi.support.HistoryContainer.HISTORY_WITH_ONE_EVENT
import com.jarqprog.artapi.support.HistoryContainer.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.support.HistoryContainer.HISTORY_WITH_TWO_EVENTS

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ReplayingHistoryTest {

    @Test
    fun `replay state from history without events`() {
        //given
        val identifier = Identifier.random()
        val emptyHistory = ArtHistory.with(identifier)
        val initialState = ArtAggregate.initialState(identifier)

        //when
        val replayed = ArtAggregate.replayAll(emptyHistory)

        //then
        assertThat(replayed).isEqualTo(initialState)
    }

    @Test
    fun `replay state from history with one event`() {
        //given
        val history = HISTORY_WITH_ONE_EVENT

        //when
        val replayed = ArtAggregate.replayAll(history)

        //then
        assertThat(EXPECTED_STATE_VERSION_0.isEqualTo(replayed)).isTrue()
    }

    @Test
    fun `replay state from history with two events`() {
        //given
        val history = HISTORY_WITH_TWO_EVENTS

        //when
        val replayed = ArtAggregate.replayAll(history)

        //then
        assertThat(EXPECTED_STATE_VERSION_1.isEqualTo(replayed)).isTrue()
    }

    @Test
    fun `replay state from history with three events`() {
        //given
        val history = HISTORY_WITH_THREE_EVENTS

        //when
        val replayed = ArtAggregate.replayAll(history)

        //then
        assertThat(EXPECTED_STATE_VERSION_2.isEqualTo(replayed)).isTrue()
    }
}