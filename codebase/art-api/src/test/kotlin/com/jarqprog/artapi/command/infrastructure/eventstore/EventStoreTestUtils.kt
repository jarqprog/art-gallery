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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll
import java.time.Instant
import java.util.stream.Collectors


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

internal val ANOTHER_HISTORY = ArtHistory(
        ANOTHER_EVENT_ART_CREATED.artId(),
        listOf(
                ANOTHER_EVENT_ART_CREATED,
                ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1,
                ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2,
                ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3
        )
)

internal fun assertHistoryDescriptorShouldMatchWithEvents(firstHistory: ArtHistoryDescriptor,
                                                          eventsToCompare: List<ArtEvent>) {

    val sortedSerializedEvents = firstHistory.events().sortedBy { event -> event.timestamp }
    val sortedHistory = eventsToCompare.sortedBy { event -> event.timestamp() }

    assertAll("both histories should be equal",
            { assertEquals(firstHistory.artId, sortedHistory.last().artId().value) },
            { assertEquals(firstHistory.version, sortedHistory.last().version()) },
            { assertEquals(firstHistory.timestamp, sortedHistory.last().timestamp()) },
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

internal fun assertHistoriesAreTheSame(firstHistory: ArtHistory, secondHistory: ArtHistory) {

    val firstHistoryEvents = firstHistory.events()
    val secondHistoryEvents = secondHistory.events()

    assertAll("both histories should be equal",
            { assertEquals(firstHistory.artId(), secondHistory.artId()) },
            { assertEquals(firstHistory.version(), secondHistory.version()) },
            { assertEquals(firstHistory.timestamp(), secondHistory.timestamp()) },
            { assertEquals(firstHistory.size(), secondHistory.size()) },
            {
                for (index in secondHistoryEvents.indices) {
                    assertEquals(firstHistoryEvents[index].artId(), secondHistoryEvents[index].artId())
                    assertEquals(firstHistoryEvents[index].version(), secondHistoryEvents[index].version())
                    assertEquals(firstHistoryEvents[index].timestamp(), secondHistoryEvents[index].timestamp())
                    assertEquals(firstHistoryEvents[index].eventName(), secondHistoryEvents[index].eventName())
                    assertEquals(firstHistoryEvents[index].eventType(), secondHistoryEvents[index].eventType())
                }
            }
    )
}

internal fun filterHistoryByPointInTime(history: ArtHistory, pointInTime: Instant): ArtHistory {
    return ArtHistory(
            history.artId(),
            history.events().stream()
                    .filter { event -> event.timestamp() <= pointInTime }
                    .collect(Collectors.toList())
    )
}

internal fun filterEventsByPointInTime(history: List<ArtEvent>, pointInTime: Instant): List<ArtEvent> {
    return history.stream()
            .filter { event -> event.timestamp() <= pointInTime }
            .collect(Collectors.toList())
}
