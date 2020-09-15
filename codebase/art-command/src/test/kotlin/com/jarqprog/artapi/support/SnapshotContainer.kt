package com.jarqprog.artapi.support

import com.jarqprog.artapi.domain.art.ArtAggregate
import com.jarqprog.artapi.domain.art.ArtHistory
import com.jarqprog.artapi.domain.art.ArtTestUtils.eventsToDescriptors
import com.jarqprog.artapi.support.EventContainer.ANOTHER_EVENT_ART_CREATED
import com.jarqprog.artapi.support.EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1
import com.jarqprog.artapi.support.EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2
import com.jarqprog.artapi.support.EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3
import com.jarqprog.artapi.support.EventContainer.ANY_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.ANY_OTHER_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.EVENT_ART_CREATED
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V1
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V2
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V3
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V4
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V5

object SnapshotContainer {

    val SNAPSHOT_V0 = ArtAggregate.replayAll(
            ArtHistory.with(
                    ANY_IDENTIFIER,
                    eventsToDescriptors(listOf(EVENT_ART_CREATED))
            )
    )

    val SNAPSHOT_V1 = ArtAggregate.replayAll(
            ArtHistory.with(
                    ANY_IDENTIFIER,
                    eventsToDescriptors(listOf(EVENT_ART_CREATED, EVENT_RESOURCE_URL_CHANGED_V1))
            )
    )

    val SNAPSHOT_V2 = ArtAggregate.replayAll(
            ArtHistory.with(
                    ANY_IDENTIFIER,
                    eventsToDescriptors(
                            listOf(
                                    EVENT_ART_CREATED,
                                    EVENT_RESOURCE_URL_CHANGED_V1,
                                    EVENT_RESOURCE_URL_CHANGED_V2
                            )
                    )
            )
    )

    val SNAPSHOT_V3 = ArtAggregate.replayAll(
            ArtHistory.with(
                    ANY_IDENTIFIER,
                    eventsToDescriptors(
                            listOf(
                                    EVENT_ART_CREATED,
                                    EVENT_RESOURCE_URL_CHANGED_V1,
                                    EVENT_RESOURCE_URL_CHANGED_V2,
                                    EVENT_RESOURCE_URL_CHANGED_V3
                            )
                    )
            )
    )

    val SNAPSHOT_V4 = ArtAggregate.replayAll(
            ArtHistory.with(
                    ANY_IDENTIFIER,
                    eventsToDescriptors(
                            listOf(
                                    EVENT_ART_CREATED,
                                    EVENT_RESOURCE_URL_CHANGED_V1,
                                    EVENT_RESOURCE_URL_CHANGED_V2,
                                    EVENT_RESOURCE_URL_CHANGED_V3,
                                    EVENT_RESOURCE_URL_CHANGED_V4
                            )
                    )
            )
    )

    val SNAPSHOT_V5 = ArtAggregate.replayAll(
            ArtHistory.with(
                    ANY_IDENTIFIER,
                    eventsToDescriptors(
                            listOf(
                                    EVENT_ART_CREATED,
                                    EVENT_RESOURCE_URL_CHANGED_V1,
                                    EVENT_RESOURCE_URL_CHANGED_V2,
                                    EVENT_RESOURCE_URL_CHANGED_V3,
                                    EVENT_RESOURCE_URL_CHANGED_V4,
                                    EVENT_RESOURCE_URL_CHANGED_V5
                            )
                    )
            )
    )

    val ANOTHER_SNAPSHOT_V0 = ArtAggregate.replayAll(
            ArtHistory.with(
                    ANY_OTHER_IDENTIFIER,
                    eventsToDescriptors(listOf(ANOTHER_EVENT_ART_CREATED))
            )
    )

    val ANOTHER_SNAPSHOT_V1 = ArtAggregate.replayAll(
            ArtHistory.with(
                    ANY_OTHER_IDENTIFIER,
                    eventsToDescriptors(listOf(ANOTHER_EVENT_ART_CREATED, ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1))
            )
    )

    val ANOTHER_SNAPSHOT_V2 = ArtAggregate.replayAll(
            ArtHistory.with(
                    ANY_OTHER_IDENTIFIER,
                    eventsToDescriptors(
                            listOf(
                                    ANOTHER_EVENT_ART_CREATED,
                                    ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1,
                                    ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2
                            )
                    )
            )
    )

    val ANOTHER_SNAPSHOT_V3 = ArtAggregate.replayAll(
            ArtHistory.with(
                    ANY_OTHER_IDENTIFIER,
                    eventsToDescriptors(
                            listOf(
                                    ANOTHER_EVENT_ART_CREATED,
                                    ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1,
                                    ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2,
                                    ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3
                            )
                    )
            )
    )
}