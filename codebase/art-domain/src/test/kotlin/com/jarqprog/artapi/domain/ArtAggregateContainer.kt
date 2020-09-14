package com.jarqprog.artapi.domain

import com.jarqprog.artapi.domain.HistoryContainer.HISTORY_WITH_THREE_EVENTS

object ArtAggregateContainer {

    val SNAPSHOT_V2 = ArtAggregate.replayAll(HISTORY_WITH_THREE_EVENTS)

    val EXPECTED_STATE_VERSION_0 = TestArt(
            EventContainer.EVENT_ART_CREATED.artId(),
            EventContainer.EVENT_ART_CREATED.version(),
            EventContainer.EVENT_ART_CREATED.timestamp(),
            EventContainer.EVENT_ART_CREATED.author(),
            EventContainer.EVENT_ART_CREATED.resource(),
            EventContainer.EVENT_ART_CREATED.addedBy(),
            EventContainer.EVENT_ART_CREATED.artGenre(),
            EventContainer.EVENT_ART_CREATED.artStatus()
    )


    val EXPECTED_STATE_VERSION_1 = TestArt(
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V1.artId(),
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V1.version(),
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V1.timestamp(),
            EventContainer.EVENT_ART_CREATED.author(),
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V1.resource(),
            EventContainer.EVENT_ART_CREATED.addedBy(),
            EventContainer.EVENT_ART_CREATED.artGenre(),
            EventContainer.EVENT_ART_CREATED.artStatus()
    )

    val EXPECTED_STATE_VERSION_2 = TestArt(
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V2.artId(),
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V2.version(),
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V2.timestamp(),
            EventContainer.EVENT_ART_CREATED.author(),
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V2.resource(),
            EventContainer.EVENT_ART_CREATED.addedBy(),
            EventContainer.EVENT_ART_CREATED.artGenre(),
            EventContainer.EVENT_ART_CREATED.artStatus()
    )
}