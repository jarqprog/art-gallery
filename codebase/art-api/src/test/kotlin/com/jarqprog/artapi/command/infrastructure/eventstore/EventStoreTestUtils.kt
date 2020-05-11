package com.jarqprog.artapi.command.infrastructure.eventstore


import com.jarqprog.artapi.command.*
import com.jarqprog.artapi.command.ANY_OTHER_RESOURCE
import com.jarqprog.artapi.command.ANY_RESOURCE
import com.jarqprog.artapi.command.ANY_RESOURCE_WITH_LONG_PATH
import com.jarqprog.artapi.command.AUTHOR_MARIA
import com.jarqprog.artapi.command.TIME_NOW
import com.jarqprog.artapi.command.USER_MARIA
import com.jarqprog.artapi.command.artdomain.ArtGenre
import com.jarqprog.artapi.command.artdomain.ArtHistory
import com.jarqprog.artapi.command.artdomain.ArtStatus
import com.jarqprog.artapi.command.artdomain.events.ArtCreated
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.events.ResourceChanged
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.ArtHistoryDescriptor
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll


internal val ANOTHER_EVENT_ART_CREATED = ArtCreated(
        ANY_OTHER_IDENTIFIER,
        0,
        TIME_NOW.minusSeconds(1200),
        AUTHOR_MARIA,
        ANY_RESOURCE,
        USER_MARIA,
        ArtGenre.UNDEFINED,
        ArtStatus.ACTIVE
)

internal val ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1 = ResourceChanged(
        ANY_OTHER_IDENTIFIER,
        1,
        TIME_NOW.minusSeconds(600),
        ANY_RESOURCE_WITH_LONG_PATH
)

internal val ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2 = ResourceChanged(
        ANY_OTHER_IDENTIFIER,
        2,
        TIME_NOW.minusSeconds(120),
        ANY_OTHER_RESOURCE
)

internal val ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3 = ResourceChanged(
        ANY_OTHER_IDENTIFIER,
        3,
        TIME_NOW,
        ANY_OTHER_RESOURCE
)

internal val ANOTHER_HISTORY = listOf(
        ANOTHER_EVENT_ART_CREATED,
        ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1,
        ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2,
        ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3
)


internal fun assertHistoryDescriptorShouldMatchWithEvents(historyDescriptor: ArtHistoryDescriptor,
                                                          eventsToCompare: List<ArtEvent>) {

    val sortedSerializedEvents = historyDescriptor.events().sortedBy { event -> event.version }
    val sortedHistory = eventsToCompare.sortedBy { event -> event.version() }

    assertAll("both histories should match",
            { assertEquals(historyDescriptor.artId, sortedHistory.last().artId().value) },
            { assertEquals(historyDescriptor.version, sortedHistory.last().version()) },
            { assertEquals(historyDescriptor.timestamp, sortedHistory.last().timestamp()) },
            { assertEquals(sortedSerializedEvents.size, sortedHistory.size) },
            {
                for (index in sortedHistory.indices) {
                    assertEquals(Identifier(sortedSerializedEvents[index].artId), sortedHistory[index].artId())
                    assertEquals(sortedSerializedEvents[index].version, sortedHistory[index].version())
                    assertEquals(sortedSerializedEvents[index].eventName, sortedHistory[index].eventName())
                    assertEquals(sortedSerializedEvents[index].eventType, sortedHistory[index].eventType())
                    assertEquals(sortedSerializedEvents[index].timestamp, sortedHistory[index].timestamp())
                }
            }
    )
}

internal fun assertHistoryShouldMatchWithEvents(history: ArtHistory, eventsToCompare: List<ArtEvent>) {

    val sortedHistory = history.events.sortedBy { event -> event.version() }
    val sortedEvents = eventsToCompare.sortedBy { event -> event.version() }

    assertAll("both histories should match",
            { assertEquals(history.artId, sortedEvents.last().artId()) },
            { assertEquals(history.version, sortedEvents.last().version()) },
            { assertEquals(history.timestamp, sortedEvents.last().timestamp()) },
            { assertEquals(sortedHistory.size, sortedEvents.size) },
            {
                for (index in sortedEvents.indices) {
                    assertEquals(sortedHistory[index].artId(), sortedEvents[index].artId())
                    assertEquals(sortedHistory[index].version(), sortedEvents[index].version())
                    assertEquals(sortedHistory[index].timestamp(), sortedEvents[index].timestamp())
                    assertEquals(sortedHistory[index].eventName(), sortedEvents[index].eventName())
                    assertEquals(sortedHistory[index].eventType(), sortedEvents[index].eventType())
                }
            }
    )
}

internal fun assertHistoriesAreTheSame(firstHistory: List<ArtEvent>, secondHistory: List<ArtEvent>) {

    assertAll("history should be the same",
            { assertArrayEquals(firstHistory.toTypedArray(), secondHistory.toTypedArray()) }
    )
}

internal fun mergeAndSortTwoHistories(firstHistory: List<ArtEvent>, secondHistory: List<ArtEvent>): List<ArtEvent> {

    val merged = firstHistory.toMutableList()
    merged.addAll(secondHistory)
    return merged.sortedBy { event -> event.version() }.toList()

}
