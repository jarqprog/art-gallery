package com.jarqprog.artapi.domain.replaying

import com.jarqprog.artapi.domain.*
import com.jarqprog.artapi.domain.HISTORY_WITH_ONE_EVENT
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


internal class ReplayingWithSnapshot {

    @Test
    @DisplayName("replay state from snapshot v0 and three events history")
    fun replayFromSnapshotV0AndThreeEvents() {

        val snapshotV0 = ArtAggregate.replayAll(HISTORY_WITH_ONE_EVENT)
        val historyWithSnapshotAndEvents = ArtHistory.withSnapshot(snapshotV0, THREE_EVENTS_LIST)

        val replayed = ArtAggregate.replayAll(historyWithSnapshotAndEvents)

        assertStatesEquals(EXPECTED_STATE_VERSION_2, replayed)
    }

    @Test
    @DisplayName("replay state from snapshot v0 and one events history")
    fun replayFromSnapshotV0AndOneEvent() {

        val snapshotV0 = ArtAggregate.replayAll(HISTORY_WITH_ONE_EVENT)

        val historyWithSnapshotAndIgnoredEvent = ArtHistory.withSnapshot(snapshotV0, ONE_EVENT_LIST)

        val replayed = ArtAggregate.replayAll(historyWithSnapshotAndIgnoredEvent)

        assertStatesEquals(snapshotV0, historyWithSnapshotAndIgnoredEvent.snapshot())
        assertStatesEquals(snapshotV0, replayed)
    }


    @Test
    @DisplayName("replay state from snapshot v1 and three events history")
    fun replayFromSnapshotV1AndThreeEvents() {

        val snapshotV1 = ArtAggregate.replayAll(HISTORY_WITH_TWO_EVENTS)
        val historyWithSnapshotAndEvents = ArtHistory.withSnapshot(snapshotV1, THREE_EVENTS_LIST)

        val replayed = ArtAggregate.replayAll(historyWithSnapshotAndEvents)

        assertStatesEquals(EXPECTED_STATE_VERSION_2, replayed)
    }

    @Test
    @DisplayName("replay state from snapshot v2 and outdated history")
    fun replayFromSnapshotV2WithOutdatedHistory() {

        val snapshotV2 = ArtAggregate.replayAll(HISTORY_WITH_THREE_EVENTS)
        val historyWithSnapshotAndOutdatedEvents = ArtHistory.withSnapshot(snapshotV2, ONE_EVENT_LIST)

        val replayed = ArtAggregate.replayAll(historyWithSnapshotAndOutdatedEvents)

        assertStatesEquals(snapshotV2, replayed)
    }
}