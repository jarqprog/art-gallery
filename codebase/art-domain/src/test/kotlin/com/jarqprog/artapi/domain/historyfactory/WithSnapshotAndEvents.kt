package com.jarqprog.artapi.domain.historyfactory

import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.ArtAggregateContainer.SNAPSHOT_V2
import com.jarqprog.artapi.domain.ArtTestAssertions.assertHistoryAndEventsValuesMatch
import com.jarqprog.artapi.domain.ArtTestAssertions.assertStatesEquals
import com.jarqprog.artapi.domain.EventContainer.EVENTS_V_3_4_5
import com.jarqprog.artapi.domain.EventContainer.THREE_EVENTS_LIST
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class WithSnapshotAndEvents {

    @Test
    fun shouldCreateHistoryWithSnapshotAndEvents() {

        val history = ArtHistory.withSnapshot(SNAPSHOT_V2, EVENTS_V_3_4_5)

        assertStatesEquals(SNAPSHOT_V2, history.snapshot())
        Assertions.assertTrue(history.isNotEmpty())
        assertHistoryAndEventsValuesMatch(EVENTS_V_3_4_5, history)
    }

    @Test
    fun shouldIgnoreOlderEvents() {

        val history = ArtHistory.withSnapshot(SNAPSHOT_V2, EVENTS_V_3_4_5.plus(THREE_EVENTS_LIST))

        assertStatesEquals(SNAPSHOT_V2, history.snapshot())
        Assertions.assertTrue(history.isNotEmpty())
        assertHistoryAndEventsValuesMatch(EVENTS_V_3_4_5, history)
    }

    @Test
    fun shouldIgnoreOlderEventsIfEventsAreInReversedOrder() {

        val history = ArtHistory.withSnapshot(SNAPSHOT_V2, EVENTS_V_3_4_5.plus(THREE_EVENTS_LIST).reversed())

        assertStatesEquals(SNAPSHOT_V2, history.snapshot())
        Assertions.assertTrue(history.isNotEmpty())
        assertHistoryAndEventsValuesMatch(EVENTS_V_3_4_5, history)
    }
}