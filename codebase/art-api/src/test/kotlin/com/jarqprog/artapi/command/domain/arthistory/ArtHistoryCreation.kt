package com.jarqprog.artapi.command.domain.arthistory

import com.jarqprog.artapi.command.ANY_IDENTIFIER
import com.jarqprog.artapi.command.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.command.domain.ArtHistory
import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.ports.outgoing.eventstore.assertHistoriesAreTheSame
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.Instant

internal class ArtHistoryCreation {

    @Test
    fun initializedHistoryShouldHaveProperValues() {

        val history = ArtHistory.initialize(ANY_IDENTIFIER)
        val expectedVersion = -1
        val expectedTimestamp = Instant.MIN
        val expectedEvents = emptyList<ArtEvent>()

        assertAll("values should match",
                { assertEquals(ANY_IDENTIFIER, history.artId()) },
                { assertEquals(expectedVersion, history.version()) },
                { assertEquals(expectedTimestamp, history.timestamp()) },
                { assertEquals(expectedEvents, history.events()) }
        )
    }

    @Test
    fun historyCreatedWithEventsShouldHaveProperValues() {

        val history = ArtHistory(ANY_IDENTIFIER, HISTORY_WITH_THREE_EVENTS.events())

        assertHistoriesAreTheSame(HISTORY_WITH_THREE_EVENTS, history)
    }

    @Test
    fun historyCreatedWithEventsInReversedOrderShouldHaveProperValues() {

        val history = ArtHistory(ANY_IDENTIFIER, HISTORY_WITH_THREE_EVENTS.events().reversed())

        assertHistoriesAreTheSame(HISTORY_WITH_THREE_EVENTS, history)
    }
}