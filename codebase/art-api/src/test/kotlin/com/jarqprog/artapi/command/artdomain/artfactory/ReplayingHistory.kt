package com.jarqprog.artapi.command.artdomain.artfactory

import com.jarqprog.artapi.command.artdomain.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


internal class ReplayingHistory {

    @Test
    @DisplayName("replay state from history with one event")
    fun replayHistoryWithOneEvent() {

        val replayed = Art.replayAll(ANY_IDENTIFIER, HISTORY_WITH_ONE_EVENT)

        assertStatesEquals(EXPECTED_STATE_VERSION_0, replayed)
    }

    @Test
    @DisplayName("replay state from history with two events")
    fun replayHistoryWithTwoEvent() {

        val replayed = Art.replayAll(ANY_IDENTIFIER, HISTORY_WITH_TWO_EVENTS)

        assertStatesEquals(EXPECTED_STATE_VERSION_1, replayed)
    }

    @Test
    @DisplayName("replay state from history with three events")
    fun replayHistoryWithThreeEvents() {

        val replayed = Art.replayAll(ANY_IDENTIFIER, HISTORY_WITH_THREE_EVENTS)

        assertStatesEquals(EXPECTED_STATE_VERSION_2, replayed)
    }

    @Test
    @DisplayName("replay state from history in reversed order")
    fun replayReversedHistory() {

        val replayed = Art.replayAll(ANY_IDENTIFIER, HISTORY_WITH_THREE_EVENTS.reversed())

        assertStatesEquals(EXPECTED_STATE_VERSION_2, replayed)
    }
}