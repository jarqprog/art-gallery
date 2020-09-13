package com.jarqprog.artapi.ports.outgoing.eventstore.entity.snapshot

import com.jarqprog.artapi.ports.outgoing.eventstore.entity.Snapshot
import com.jarqprog.artapi.ports.outgoing.eventstore.entity.SnapshotCreation
import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.ports.outgoing.eventstore.entity.EntityAssertions.assertArtAndSnapshotValuesMatch
import com.jarqprog.artapi.support.EventContainer.ANY_IDENTIFIER
import com.jarqprog.artapi.support.HistoryContainer.HISTORY_WITH_THREE_EVENTS
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.function.Function

internal class CreatingSnapshot {

    private lateinit var underTest: Function<ArtAggregate, Snapshot>

    @BeforeEach
    private fun prepareTest() {
        underTest = SnapshotCreation()
    }

    @Test
    fun shouldCreateSnapshotFromInitialState() {

        val initialState = ArtAggregate.initialState(ANY_IDENTIFIER)

        val snapshot = underTest.apply(initialState)

        assertArtAndSnapshotValuesMatch(initialState, snapshot)
    }

    @Test
    fun shouldCreateSnapshotFromArtV2() {

        val artV2 = ArtAggregate.replayAll(HISTORY_WITH_THREE_EVENTS)

        val snapshot = underTest.apply(artV2)

        assertArtAndSnapshotValuesMatch(artV2, snapshot)
    }
}