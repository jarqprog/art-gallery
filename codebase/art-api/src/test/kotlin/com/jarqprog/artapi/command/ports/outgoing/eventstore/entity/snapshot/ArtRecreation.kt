package com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.snapshot

import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.Snapshot
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.SnapshotCreation
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.SnapshotToArt
import com.jarqprog.artapi.domain.ANY_IDENTIFIER
import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.domain.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.domain.assertStatesEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.function.Function

internal class ArtRecreation {

    private lateinit var transformToSnapshot: Function<ArtAggregate, Snapshot>
    private lateinit var underTest: Function<Snapshot, ArtAggregate>

    @BeforeEach
    private fun prepareTest() {
        transformToSnapshot = SnapshotCreation()
        underTest = SnapshotToArt()
    }

    @Test
    fun shouldRecreateUsingInitialState() {

        val initialState = ArtAggregate.initialState(ANY_IDENTIFIER)

        val snapshot = transformToSnapshot.apply(initialState)

        val recreated = underTest.apply(snapshot)

        assertStatesEquals(initialState, recreated)
    }

    @Test
    fun shouldRecreateUsingArtV2() {

        val artV2 = ArtAggregate.replayAll(HISTORY_WITH_THREE_EVENTS)

        val snapshot = transformToSnapshot.apply(artV2)

        val recreated = underTest.apply(snapshot)

        assertStatesEquals(artV2, recreated)
    }
}