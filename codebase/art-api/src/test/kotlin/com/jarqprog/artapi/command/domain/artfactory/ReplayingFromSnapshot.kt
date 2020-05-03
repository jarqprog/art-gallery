package com.jarqprog.artapi.command.domain.artfactory

import com.jarqprog.artapi.command.domain.Art
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


internal class ReplayingFromSnapshot {

    @Test
    @DisplayName("replay state from snapshot v0 and three events history")
    fun replayFromSnapshotV0AndThreeEvents() {

        val snapshot = Art.replayAll(ANY_UUID, HISTORY_WITH_ONE_EVENT)
        val replayed = Art.replayFromSnapshot(snapshot, HISTORY_WITH_THREE_EVENTS)

        assertStatesEquals(EXPECTED_STATE_VERSION_2, replayed)
    }

    @Test
    @DisplayName("replay state from snapshot v0 and three events history reversed")
    fun replayFromSnapshotV0AndThreeEventsReversed() {

        val snapshot = Art.replayAll(ANY_UUID, HISTORY_WITH_ONE_EVENT)
        val replayed = Art.replayFromSnapshot(snapshot, HISTORY_WITH_THREE_EVENTS.reversed())

        assertStatesEquals(EXPECTED_STATE_VERSION_2, replayed)
    }

    @Test
    @DisplayName("replay state from snapshot v0 and one events history")
    fun replayFromSnapshotV0AndOneEvent() {

        val snapshot = Art.replayAll(ANY_UUID, HISTORY_WITH_ONE_EVENT)
        val replayed = Art.replayFromSnapshot(snapshot, HISTORY_WITH_ONE_EVENT)

        assertStatesEquals(snapshot, replayed)
    }

    @Test
    @DisplayName("replay state from snapshot v1 and three events history")
    fun replayFromSnapshotV1AndThreeEvents() {

        val snapshot = Art.replayAll(ANY_UUID, HISTORY_WITH_TWO_EVENTS)
        val replayed = Art.replayFromSnapshot(snapshot, HISTORY_WITH_THREE_EVENTS)

        assertStatesEquals(EXPECTED_STATE_VERSION_2, replayed)
    }

    @Test
    @DisplayName("replay state from snapshot v2 and three events history")
    fun replayFromSnapshotV2WithOutdatedHistory() {

        val snapshot = Art.replayAll(ANY_UUID, HISTORY_WITH_THREE_EVENTS)
        val replayed = Art.replayFromSnapshot(snapshot, HISTORY_WITH_ONE_EVENT)

        assertStatesEquals(snapshot, replayed)
    }
}