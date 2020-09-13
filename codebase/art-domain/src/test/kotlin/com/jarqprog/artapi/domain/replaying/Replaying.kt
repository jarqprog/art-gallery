package com.jarqprog.artapi.domain.replaying

import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.ArtAggregateSupport.EXPECTED_STATE_VERSION_0
import com.jarqprog.artapi.domain.ArtAggregateSupport.EXPECTED_STATE_VERSION_1
import com.jarqprog.artapi.domain.ArtAggregateSupport.EXPECTED_STATE_VERSION_2
import com.jarqprog.artapi.domain.ArtTestAssertions.assertStatesEquals
import com.jarqprog.artapi.domain.HistorySupport.HISTORY_WITH_ONE_EVENT
import com.jarqprog.artapi.domain.HistorySupport.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.domain.HistorySupport.HISTORY_WITH_TWO_EVENTS
import com.jarqprog.artapi.domain.vo.Identifier
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


internal class Replaying {

    @Test
    @DisplayName("replay state from history without events")
    fun replayHistoryWithoutEvents() {

        val identifier = Identifier.random()
        val emptyHistory = ArtHistory.initialize(identifier)
        val initialState = ArtAggregate.initialState(identifier)

        val replayed = ArtAggregate.replayAll(emptyHistory)

        assertStatesEquals(initialState, replayed)
    }

    @Test
    @DisplayName("replay state from history with one event")
    fun replayHistoryWithOneEvent() {

        val replayed = ArtAggregate.replayAll(HISTORY_WITH_ONE_EVENT)

        assertStatesEquals(EXPECTED_STATE_VERSION_0, replayed)
    }

    @Test
    @DisplayName("replay state from history with two events")
    fun replayHistoryWithTwoEvent() {

        val replayed = ArtAggregate.replayAll(HISTORY_WITH_TWO_EVENTS)

        assertStatesEquals(EXPECTED_STATE_VERSION_1, replayed)
    }

    @Test
    @DisplayName("replay state from history with three events")
    fun replayHistoryWithThreeEvents() {

        val replayed = ArtAggregate.replayAll(HISTORY_WITH_THREE_EVENTS)

        assertStatesEquals(EXPECTED_STATE_VERSION_2, replayed)
    }
}