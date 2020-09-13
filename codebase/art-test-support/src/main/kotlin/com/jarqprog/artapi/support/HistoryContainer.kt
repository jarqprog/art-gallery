package com.jarqprog.artapi.support

import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.support.EventContainer.ONE_EVENT_LIST
import com.jarqprog.artapi.support.EventContainer.THREE_EVENTS_LIST
import com.jarqprog.artapi.support.EventContainer.TWO_EVENT_LIST

object HistoryContainer {

    val HISTORY_WITH_ONE_EVENT = ArtHistory.withEvents(ONE_EVENT_LIST)

    val HISTORY_WITH_TWO_EVENTS = ArtHistory.withEvents(TWO_EVENT_LIST)

    val HISTORY_WITH_THREE_EVENTS = ArtHistory.withEvents(THREE_EVENTS_LIST)

    val ANOTHER_HISTORY = ArtHistory.withEvents(
            listOf(
                    EventContainer.ANOTHER_EVENT_ART_CREATED,
                    EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1,
                    EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2,
                    EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3
            )
    )
}
