package com.jarqprog.artapi.command.artdomain.commanddispatching

import com.jarqprog.artapi.command.artdomain.ArtGenre
import com.jarqprog.artapi.command.artdomain.ArtStatus
import com.jarqprog.artapi.command.artdomain.events.ArtCreated
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.events.ResourceChanged
import com.jarqprog.artapi.command.artdomain.vo.Author
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.artdomain.vo.Resource
import com.jarqprog.artapi.command.artdomain.vo.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertAll
import java.time.Instant
import java.util.*

internal val ANY_IDENTIFIER: Identifier = Identifier("2aa41ef2-11cc-427f-aa7a-98158b9435fd")
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

internal val HISTORY_WITHOUT_EVENTS = emptyList<ArtEvent>()

internal val HISTORY_WITH_TWO_EVENTS = listOf(EVENT_ART_CREATED, EVENT_RESOURCE_URL_CHANGED_V1)

internal fun assertArtCreatedEventsEquals(expected: ArtCreated, event: ArtCreated) {

    assertAll("art created events should be equal - without comparing timestamps",
            { Assertions.assertEquals(expected.eventName(), event.eventName()) },
            { Assertions.assertEquals(expected.artId(), event.artId()) },
            { Assertions.assertEquals(expected.version(), event.version()) },
            { Assertions.assertEquals(expected.author(), event.author()) },
            { Assertions.assertEquals(expected.resource(), event.resource()) },
            { Assertions.assertEquals(expected.addedBy(), event.addedBy()) },
            { Assertions.assertEquals(expected.artGenre(), event.artGenre()) },
            { Assertions.assertEquals(expected.artStatus(), event.artStatus()) }
    )
}

