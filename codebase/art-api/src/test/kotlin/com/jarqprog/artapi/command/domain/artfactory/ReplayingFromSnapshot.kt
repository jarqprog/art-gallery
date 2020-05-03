package com.jarqprog.artapi.command.domain.artfactory

import com.jarqprog.artapi.command.domain.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


internal class ReplayingFromSnapshot {

    @Test
    @DisplayName("replay state from snapshot v0 and three events history")
    fun replayFromSnapshotV0AndThreeEvents() {

        val expected = Art(ANY_UUID, 2, ANY_AUTHOR, ANY_OTHER_RESOURCE_URL, THREE_EVENTS_HISTORY)

        val snapshot = Art.replayAll(ANY_UUID, ONE_EVENT_HISTORY)
        val replayed = Art.replayFromSnapshot(snapshot, THREE_EVENTS_HISTORY)

        assertStatesEquals(expected, replayed)
    }

    @Test
    @DisplayName("replay state from snapshot v0 and three events history reversed")
    fun replayFromSnapshotV0AndThreeEventsReversed() {

        val expected = Art(ANY_UUID, 2, ANY_AUTHOR, ANY_OTHER_RESOURCE_URL, THREE_EVENTS_HISTORY)

        val snapshot = Art.replayAll(ANY_UUID, ONE_EVENT_HISTORY)
        val replayed = Art.replayFromSnapshot(snapshot, THREE_EVENTS_HISTORY.reversed())

        assertStatesEquals(expected, replayed)
    }

    @Test
    @DisplayName("replay state from snapshot v0 and one events history")
    fun replayFromSnapshotV0AndOneEvent() {

        val snapshot = Art.replayAll(ANY_UUID, ONE_EVENT_HISTORY)
        val replayed = Art.replayFromSnapshot(snapshot, ONE_EVENT_HISTORY)

        assertStatesEquals(snapshot, replayed)
    }

    @Test
    @DisplayName("replay state from snapshot v1 and three events history")
    fun replayFromSnapshotV1AndThreeEvents() {

        val expected = Art(ANY_UUID, 2, ANY_AUTHOR, ANY_OTHER_RESOURCE_URL, THREE_EVENTS_HISTORY)

        val snapshot = Art.replayAll(ANY_UUID, TWO_EVENTS_HISTORY)
        val replayed = Art.replayFromSnapshot(snapshot, THREE_EVENTS_HISTORY)

        assertStatesEquals(expected, replayed)
    }

    @Test
    @DisplayName("replay state from snapshot v2 and three events history")
    fun replayFromSnapshotV2WithOutdatedHistory() {

        val snapshot = Art.replayAll(ANY_UUID, THREE_EVENTS_HISTORY)
        val replayed = Art.replayFromSnapshot(snapshot, ONE_EVENT_HISTORY)

        assertStatesEquals(snapshot, replayed)
    }
}