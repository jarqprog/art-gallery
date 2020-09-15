package com.jarqprog.artapi.support

import com.jarqprog.artapi.support.EventContainer.ONE_EVENT_LIST
import com.jarqprog.artapi.support.EventContainer.THREE_EVENTS_LIST
import com.jarqprog.artapi.support.EventContainer.TWO_EVENT_LIST
import com.jarqprog.artapi.domain.art.ArtHistory
import com.jarqprog.artapi.domain.art.ArtTestUtils.eventsToDescriptors
import com.jarqprog.artapi.support.EventContainer.ANY_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.ANY_OTHER_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.SIX_EVENTS_LIST

object HistoryContainer {

    val HISTORY_WITH_ONE_EVENT = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(ONE_EVENT_LIST))

    val HISTORY_WITH_TWO_EVENTS = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(TWO_EVENT_LIST))

    val HISTORY_WITH_THREE_EVENTS = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(THREE_EVENTS_LIST))

    val COMPLETE_FIRST_HISTORY = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(SIX_EVENTS_LIST))

    val COMPLETE_SECOND_HISTORY = ArtHistory.with(
            ANY_OTHER_IDENTIFIER,
            eventsToDescriptors(
                    listOf(
                            EventContainer.ANOTHER_EVENT_ART_CREATED,
                            EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1,
                            EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2,
                            EventContainer.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3
                    )
            )
    )
}