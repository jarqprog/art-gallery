package com.jarqprog.artapi.domain.historyfactory

import com.jarqprog.artapi.domain.ANY_IDENTIFIER
import com.jarqprog.artapi.domain.ArtAggregate.Factory.INITIAL_VERSION
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.events.ArtEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.Instant

internal class WithIdentifier {

    @Test
    fun initializedHistoryShouldHaveProperValues() {

        val history = ArtHistory.initialize(ANY_IDENTIFIER)
        val expectedVersion = INITIAL_VERSION
        val expectedTimestamp = Instant.MIN
        val expectedEvents = emptyList<ArtEvent>()

        assertAll("values should match",
                { assertEquals(ANY_IDENTIFIER, history.artId()) },
                { assertEquals(expectedVersion, history.version()) },
                { assertEquals(expectedTimestamp, history.timestamp()) },
                { assertEquals(expectedEvents, history.events()) }
        )
    }
}