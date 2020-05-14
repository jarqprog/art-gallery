package com.jarqprog.artapi.command

import com.jarqprog.artapi.command.domain.*
import com.jarqprog.artapi.command.domain.events.ArtCreated
import com.jarqprog.artapi.command.domain.events.ResourceChanged
import com.jarqprog.artapi.command.domain.vo.Author
import com.jarqprog.artapi.command.domain.vo.Identifier
import com.jarqprog.artapi.command.domain.vo.Resource
import com.jarqprog.artapi.command.domain.vo.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll
import java.time.Instant

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
        TIME_NOW.minusSeconds(5),
        AUTHOR_MARIA,
        ANY_RESOURCE,
        USER_MARIA,
        ArtGenre.UNDEFINED,
        ArtStatus.ACTIVE
)

internal val EVENT_RESOURCE_URL_CHANGED_V1 = ResourceChanged(
        ANY_IDENTIFIER,
        1,
        TIME_NOW.minusSeconds(2),
        ANY_RESOURCE_WITH_LONG_PATH
)

internal val EVENT_RESOURCE_URL_CHANGED_V2 = ResourceChanged(
        ANY_IDENTIFIER,
        2,
        TIME_NOW,
        ANY_OTHER_RESOURCE
)

internal val HISTORY_WITH_ONE_EVENT = ArtHistory(EVENT_ART_CREATED.artId(), listOf(EVENT_ART_CREATED))
internal val HISTORY_WITH_TWO_EVENTS = ArtHistory(EVENT_ART_CREATED.artId(), listOf(EVENT_ART_CREATED,
        EVENT_RESOURCE_URL_CHANGED_V1))
internal val HISTORY_WITH_THREE_EVENTS = ArtHistory(EVENT_ART_CREATED.artId(), listOf(EVENT_ART_CREATED,
        EVENT_RESOURCE_URL_CHANGED_V1, EVENT_RESOURCE_URL_CHANGED_V2))

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

internal fun assertStatesEquals(expected: TestArt, replayed: Art) {

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

internal fun assertStatesEquals(expected: Art, replayed: Art) {

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
