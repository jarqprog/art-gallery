package com.jarqprog.artapi.domain.historyfactory

import com.jarqprog.artapi.domain.*
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