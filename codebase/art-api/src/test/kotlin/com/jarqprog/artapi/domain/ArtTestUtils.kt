package com.jarqprog.artapi.domain

import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.events.ResourceChanged
import com.jarqprog.artapi.domain.vo.Author
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.domain.vo.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll
import java.time.Instant
import java.util.Comparator

internal val ANY_IDENTIFIER: Identifier = Identifier("2aa41ef2-11cc-427f-aa7a-98158b9435fd")
internal val ANY_OTHER_IDENTIFIER: Identifier = Identifier("2aa41ef2-11cc-427f-aa7a-98158b94356a")
internal val NOT_USED_HISTORY_ID: Identifier = Identifier("2aa41ef2-11cc-427f-aa7a-98158b9435BB")
internal val TIME_NOW: Instant = Instant.now()
internal val USER_MARIA: User = User("maria_maria")
internal val AUTHOR_MARIA: Author = Author("Maria Gonzales", USER_MARIA)

internal val ANY_RESOURCE = Resource("anyResourceUrl")
internal val ANY_RESOURCE_WITH_LONG_PATH = Resource("anyResourceUrlanyResourceUrlanyResourceUrl")
internal val ANY_OTHER_RESOURCE = Resource("anyChangedResourceUrl")

internal val EVENT_ART_CREATED = ArtCreated(
        ANY_IDENTIFIER,
        0,
        TIME_NOW.minusSeconds(10),
        AUTHOR_MARIA,
        ANY_RESOURCE,
        USER_MARIA,
        ArtGenre.UNDEFINED,
        ArtStatus.ACTIVE
)

internal val EVENT_RESOURCE_URL_CHANGED_V1 = ResourceChanged(
        ANY_IDENTIFIER,
        1,
        TIME_NOW.minusSeconds(8),
        ANY_RESOURCE_WITH_LONG_PATH
)

internal val EVENT_RESOURCE_URL_CHANGED_V2 = ResourceChanged(
        ANY_IDENTIFIER,
        2,
        TIME_NOW.minusSeconds(6),
        ANY_OTHER_RESOURCE
)

internal val EVENT_RESOURCE_URL_CHANGED_V3 = ResourceChanged(
        ANY_IDENTIFIER,
        3,
        TIME_NOW.minusSeconds(4),
        ANY_RESOURCE
)

internal val EVENT_RESOURCE_URL_CHANGED_V4 = ResourceChanged(
        ANY_IDENTIFIER,
        4,
        TIME_NOW.minusSeconds(2),
        ANY_RESOURCE_WITH_LONG_PATH
)

internal val EVENT_RESOURCE_URL_CHANGED_V5 = ResourceChanged(
        ANY_IDENTIFIER,
        5,
        TIME_NOW,
        ANY_OTHER_RESOURCE
)

internal val ONE_EVENT_LIST = listOf(EVENT_ART_CREATED)
internal val HISTORY_WITH_ONE_EVENT = ArtHistory.withEvents(ONE_EVENT_LIST)

internal val TWO_EVENTS_LIST = listOf(EVENT_ART_CREATED, EVENT_RESOURCE_URL_CHANGED_V1)
internal val HISTORY_WITH_TWO_EVENTS = ArtHistory.withEvents(TWO_EVENTS_LIST)

internal val THREE_EVENTS_LIST = listOf(EVENT_ART_CREATED, EVENT_RESOURCE_URL_CHANGED_V1, EVENT_RESOURCE_URL_CHANGED_V2)
internal val HISTORY_WITH_THREE_EVENTS = ArtHistory.withEvents(THREE_EVENTS_LIST)

internal val EVENTS_V_3_4_5 = listOf(EVENT_RESOURCE_URL_CHANGED_V3, EVENT_RESOURCE_URL_CHANGED_V4,
        EVENT_RESOURCE_URL_CHANGED_V5)

internal val SNAPSHOT_V2 = ArtAggregate.replayAll(HISTORY_WITH_THREE_EVENTS)

internal val EXPECTED_STATE_VERSION_0 = TestArt(
        EVENT_ART_CREATED.artId(),
        EVENT_ART_CREATED.version(),
        EVENT_ART_CREATED.timestamp(),
        EVENT_ART_CREATED.author(),
        EVENT_ART_CREATED.resource(),
        EVENT_ART_CREATED.addedBy(),
        EVENT_ART_CREATED.artGenre(),
        EVENT_ART_CREATED.artStatus()
)


internal val EXPECTED_STATE_VERSION_1 = TestArt(
        EVENT_RESOURCE_URL_CHANGED_V1.artId(),
        EVENT_RESOURCE_URL_CHANGED_V1.version(),
        EVENT_RESOURCE_URL_CHANGED_V1.timestamp(),
        EVENT_ART_CREATED.author(),
        EVENT_RESOURCE_URL_CHANGED_V1.resource(),
        EVENT_ART_CREATED.addedBy(),
        EVENT_ART_CREATED.artGenre(),
        EVENT_ART_CREATED.artStatus()
)

internal val EXPECTED_STATE_VERSION_2 = TestArt(
        EVENT_RESOURCE_URL_CHANGED_V2.artId(),
        EVENT_RESOURCE_URL_CHANGED_V2.version(),
        EVENT_RESOURCE_URL_CHANGED_V2.timestamp(),
        EVENT_ART_CREATED.author(),
        EVENT_RESOURCE_URL_CHANGED_V2.resource(),
        EVENT_ART_CREATED.addedBy(),
        EVENT_ART_CREATED.artGenre(),
        EVENT_ART_CREATED.artStatus()
)

internal fun assertStatesEquals(expected: TestArt, replayed: ArtAggregate) {

    assertAll("art states should be equal",
            { assertEquals(expected.identifier, replayed.identifier()) },
            { assertEquals(expected.version, replayed.version()) },
            { assertEquals(expected.timestamp, replayed.timestamp()) },
            { assertEquals(expected.author, replayed.author()) },
            { assertEquals(expected.resource, replayed.resource()) },
            { assertEquals(expected.addedBy, replayed.addedBy()) },
            { assertEquals(expected.genre, replayed.genre()) },
            { assertEquals(expected.status, replayed.status()) }
    )
}

internal fun assertStatesEquals(expected: ArtAggregate, replayed: ArtAggregate) {

    assertAll("art states should be equal",
            { assertEquals(expected.identifier(), replayed.identifier()) },
            { assertEquals(expected.version(), replayed.version()) },
            { assertEquals(expected.timestamp(), replayed.timestamp()) },
            { assertEquals(expected.author(), replayed.author()) },
            { assertEquals(expected.resource(), replayed.resource()) },
            { assertEquals(expected.addedBy(), replayed.addedBy()) },
            { assertEquals(expected.genre(), replayed.genre()) },
            { assertEquals(expected.status(), replayed.status()) }
    )
}

internal fun assertArtAndHistoryValuesMatch(art: ArtAggregate, history: ArtHistory) {

    assertAll("art and history values should match",
            { assertEquals(art.identifier(), history.artId()) },
            { assertEquals(art.version(), history.version()) },
            { assertEquals(art.timestamp(), history.timestamp()) }
    )
}

internal fun assertHistoryAndEventsValuesMatch(events: List<ArtEvent>, history: ArtHistory) {

    val latestEvent = events.sortedWith(Comparator.comparing(ArtEvent::timestamp)).last()

    assertAll("history and event values should match",
            { assertEquals(latestEvent.artId(), history.artId()) },
            { assertEquals(latestEvent.version(), history.version()) },
            { assertEquals(latestEvent.timestamp(), history.timestamp()) },
            { Assertions.assertArrayEquals(events.toTypedArray(), history.events().toTypedArray()) }
    )
}


