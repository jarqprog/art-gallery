package com.jarqprog.artapi.domain.historyfactory

import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.ArtTestAssertions.assertHistoryAndEventsValuesMatch
import com.jarqprog.artapi.domain.ArtTestAssertions.assertStatesEquals
import com.jarqprog.artapi.domain.EventContainer.ONE_EVENT_LIST
import com.jarqprog.artapi.domain.EventContainer.THREE_EVENTS_LIST
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception

internal class WithEvents {

    @Test
    fun `should create history with one event`() {

        val history = ArtHistory.withEvents(ONE_EVENT_LIST)

        assertStatesEquals(ArtAggregate.initialState(history.artId()), history.snapshot())
        assertHistoryAndEventsValuesMatch(ONE_EVENT_LIST, history)
    }

    @Test
    fun `should create history with three events`() {

        val history = ArtHistory.withEvents(THREE_EVENTS_LIST)

        assertStatesEquals(ArtAggregate.initialState(history.artId()), history.snapshot())
        assertHistoryAndEventsValuesMatch(THREE_EVENTS_LIST, history)
    }

    @Test
    fun `history created with events in reversed order should have proper values`() {

        val history = ArtHistory.withEvents(THREE_EVENTS_LIST.reversed())

        assertStatesEquals(ArtAggregate.initialState(history.artId()), history.snapshot())
        assertHistoryAndEventsValuesMatch(THREE_EVENTS_LIST, history)
    }

    @Test
    fun `should not create history using empty event list`() {

        assertThrows<Exception> { ArtHistory.withEvents(emptyList()) }
    }
}