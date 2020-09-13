package com.jarqprog.artapi.domain.replaying

import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.ArtAggregateSupport.EXPECTED_STATE_VERSION_2
import com.jarqprog.artapi.domain.ArtTestAssertions.assertStatesEquals
import com.jarqprog.artapi.domain.EventSupport.ONE_EVENT_LIST
import com.jarqprog.artapi.domain.EventSupport.THREE_EVENTS_LIST
import com.jarqprog.artapi.domain.HistorySupport.HISTORY_WITH_ONE_EVENT
import com.jarqprog.artapi.domain.HistorySupport.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.domain.HistorySupport.HISTORY_WITH_TWO_EVENTS
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