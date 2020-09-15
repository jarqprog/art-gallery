package com.jarqprog.artapi.ports.outgoing.eventstore

import com.jarqprog.artapi.domain.art.ProcessingResult
import com.jarqprog.artapi.support.EventContainer.ANOTHER_EVENT_ART_CREATED
import com.jarqprog.artapi.support.EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1
import com.jarqprog.artapi.support.EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2
import com.jarqprog.artapi.support.EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3
import com.jarqprog.artapi.support.EventContainer.EVENT_ART_CREATED
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V1
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V2
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V3
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V4
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V5
import com.jarqprog.artapi.support.SnapshotContainer.ANOTHER_SNAPSHOT_V0
import com.jarqprog.artapi.support.SnapshotContainer.ANOTHER_SNAPSHOT_V1
import com.jarqprog.artapi.support.SnapshotContainer.ANOTHER_SNAPSHOT_V2
import com.jarqprog.artapi.support.SnapshotContainer.ANOTHER_SNAPSHOT_V3
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V0
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V1
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V2
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V3
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V4
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V5

object EventStoreTestUtils {

    fun allFirstHistoryProcessingResults(): List<ProcessingResult> = listOf(
            ProcessingResult(EVENT_ART_CREATED, SNAPSHOT_V0),
            ProcessingResult(EVENT_RESOURCE_URL_CHANGED_V1, SNAPSHOT_V1),
            ProcessingResult(EVENT_RESOURCE_URL_CHANGED_V2, SNAPSHOT_V2),
            ProcessingResult(EVENT_RESOURCE_URL_CHANGED_V3, SNAPSHOT_V3),
            ProcessingResult(EVENT_RESOURCE_URL_CHANGED_V4, SNAPSHOT_V4),
            ProcessingResult(EVENT_RESOURCE_URL_CHANGED_V5, SNAPSHOT_V5)
    )

    fun allSecondHistoryProcessingResults(): List<ProcessingResult> = listOf(
            ProcessingResult(ANOTHER_EVENT_ART_CREATED, ANOTHER_SNAPSHOT_V0),
            ProcessingResult(ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1, ANOTHER_SNAPSHOT_V1),
            ProcessingResult(ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2, ANOTHER_SNAPSHOT_V2),
            ProcessingResult(ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3, ANOTHER_SNAPSHOT_V3)
    )

    fun allHistoriesProcessingResultsSorted(): List<ProcessingResult> {
        return allFirstHistoryProcessingResults().plus(allSecondHistoryProcessingResults())
                .sortedBy { result -> result.artEvent.timestamp() }
    }
}
