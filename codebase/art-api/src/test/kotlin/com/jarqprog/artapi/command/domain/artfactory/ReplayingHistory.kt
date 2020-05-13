package com.jarqprog.artapi.command.domain.artfactory

import com.jarqprog.artapi.command.ANY_IDENTIFIER
import com.jarqprog.artapi.command.EXPECTED_STATE_VERSION_0
import com.jarqprog.artapi.command.EXPECTED_STATE_VERSION_1
import com.jarqprog.artapi.command.EXPECTED_STATE_VERSION_2
import com.jarqprog.artapi.command.HISTORY_WITH_ONE_EVENT
import com.jarqprog.artapi.command.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.command.HISTORY_WITH_TWO_EVENTS
import com.jarqprog.artapi.command.domain.*
import com.jarqprog.artapi.command.assertStatesEquals
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
}