package com.jarqprog.artapi.domain

import com.jarqprog.artapi.domain.HistorySupport.HISTORY_WITH_THREE_EVENTS

object ArtAggregateSupport {

    val SNAPSHOT_V2 = ArtAggregate.replayAll(HISTORY_WITH_THREE_EVENTS)

    val EXPECTED_STATE_VERSION_0 = TestArt(
            EventSupport.EVENT_ART_CREATED.artId(),
            EventSupport.EVENT_ART_CREATED.version(),
            EventSupport.EVENT_ART_CREATED.timestamp(),
            EventSupport.EVENT_ART_CREATED.author(),
            EventSupport.EVENT_ART_CREATED.resource(),
            EventSupport.EVENT_ART_CREATED.addedBy(),
            EventSupport.EVENT_ART_CREATED.artGenre(),
            EventSupport.EVENT_ART_CREATED.artStatus()
    )


    val EXPECTED_STATE_VERSION_1 = TestArt(
            EventSupport.EVENT_RESOURCE_URL_CHANGED_V1.artId(),
            EventSupport.EVENT_RESOURCE_URL_CHANGED_V1.version(),
            EventSupport.EVENT_RESOURCE_URL_CHANGED_V1.timestamp(),
            EventSupport.EVENT_ART_CREATED.author(),
            EventSupport.EVENT_RESOURCE_URL_CHANGED_V1.resource(),
            EventSupport.EVENT_ART_CREATED.addedBy(),
            EventSupport.EVENT_ART_CREATED.artGenre(),
            EventSupport.EVENT_ART_CREATED.artStatus()
    )

    val EXPECTED_STATE_VERSION_2 = TestArt(
            EventSupport.EVENT_RESOURCE_URL_CHANGED_V2.artId(),
            EventSupport.EVENT_RESOURCE_URL_CHANGED_V2.version(),
            EventSupport.EVENT_RESOURCE_URL_CHANGED_V2.timestamp(),
            EventSupport.EVENT_ART_CREATED.author(),
            EventSupport.EVENT_RESOURCE_URL_CHANGED_V2.resource(),
            EventSupport.EVENT_ART_CREATED.addedBy(),
            EventSupport.EVENT_ART_CREATED.artGenre(),
            EventSupport.EVENT_ART_CREATED.artStatus()
    )

}