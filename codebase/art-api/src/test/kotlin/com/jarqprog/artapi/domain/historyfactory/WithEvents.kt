package com.jarqprog.artapi.domain.historyfactory

import com.jarqprog.artapi.domain.*
import com.jarqprog.artapi.domain.ONE_EVENT_LIST
import com.jarqprog.artapi.domain.assertHistoryAndEventsValuesMatch
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception

internal class WithEvents {

    @Test
    fun shouldCreateHistoryWithOneEvent() {

        val history = ArtHistory.withEvents(ONE_EVENT_LIST)

        assertStatesEquals(ArtAggregate.initialState(history.artId()), history.snapshot())
        assertHistoryAndEventsValuesMatch(ONE_EVENT_LIST, history)
    }

    @Test
    fun shouldCreateHistoryWithThreeEvents() {

        val history = ArtHistory.withEvents(THREE_EVENTS_LIST)

        assertStatesEquals(ArtAggregate.initialState(history.artId()), history.snapshot())
        assertHistoryAndEventsValuesMatch(THREE_EVENTS_LIST, history)
    }

    @Test
    fun historyCreatedWithEventsInReversedOrderShouldHaveProperValues() {

        val history = ArtHistory.withEvents(THREE_EVENTS_LIST.reversed())

        assertStatesEquals(ArtAggregate.initialState(history.artId()), history.snapshot())
        assertHistoryAndEventsValuesMatch(THREE_EVENTS_LIST, history)
    }

    @Test
    fun shouldNotCreateHistoryUsingEmptyEventList() {

        assertThrows<Exception> { ArtHistory.withEvents(emptyList()) }
    }
}