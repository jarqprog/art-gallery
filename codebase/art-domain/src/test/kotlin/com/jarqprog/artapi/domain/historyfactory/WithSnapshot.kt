package com.jarqprog.artapi.domain.historyfactory

import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.ArtAggregateSupport.SNAPSHOT_V2
import com.jarqprog.artapi.domain.ArtTestAssertions.assertArtAndHistoryValuesMatch
import com.jarqprog.artapi.domain.ArtTestAssertions.assertStatesEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class WithSnapshot {

    @Test
    fun shouldCreateHistoryWithSnapshot() {

        val history = ArtHistory.withSnapshot(SNAPSHOT_V2)

        assertStatesEquals(SNAPSHOT_V2, history.snapshot())
        Assertions.assertTrue(history.isEmpty())
        assertArtAndHistoryValuesMatch(SNAPSHOT_V2, history)
    }
}