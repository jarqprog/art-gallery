package com.jarqprog.artapi.support

object ArtAggregateContainer {

    val EXPECTED_STATE_VERSION_0 = ComparableArt(
            EventContainer.EVENT_ART_CREATED.artId(),
            EventContainer.EVENT_ART_CREATED.version(),
            EventContainer.EVENT_ART_CREATED.timestamp(),
            EventContainer.EVENT_ART_CREATED.author(),
            EventContainer.EVENT_ART_CREATED.resource(),
            EventContainer.EVENT_ART_CREATED.addedBy(),
            EventContainer.EVENT_ART_CREATED.artGenre(),
            EventContainer.EVENT_ART_CREATED.artStatus()
    )

    val EXPECTED_STATE_VERSION_1 = ComparableArt(
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V1.artId(),
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V1.version(),
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V1.timestamp(),
            EventContainer.EVENT_ART_CREATED.author(),
            EventContainer.EVENT_RESOURCE_URL_CHANGED_V1.resource(),
            EventContainer.EVENT_ART_CREATED.addedBy(),
            EventContainer.EVENT_ART_CREATED.artGenre(),
            EventContainer.EVENT_ART_CREATED.artStatus()
    )

    val EXPECTED_STATE_VERSION_2 = ComparableArt(
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