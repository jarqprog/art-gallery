package com.jarqprog.artapi.command.domain

import com.jarqprog.artapi.command.domain.events.ArtCreated
import com.jarqprog.artapi.command.domain.events.ResourceUrlChanged
import org.junit.jupiter.api.Assertions
import java.time.Instant
import java.util.*

internal val ANY_UUID: UUID = UUID.fromString("2aa41ef2-11cc-427f-aa7a-98158b9435fd")
internal val TIME_NOW: Instant = Instant.now()
internal const val ANY_AUTHOR = "Tom"
internal const val ANY_RESOURCE_URL = "anyResourceUrl"
internal const val ANY_OTHER_RESOURCE_URL = "anyChangedResourceUrl"

internal val EVENT_ART_CREATED = ArtCreated(
        ANY_UUID,
        0,
        TIME_NOW.minusSeconds(5),
        ANY_AUTHOR, ANY_RESOURCE_URL
)

internal val EVENT_RESOURCE_URL_CHANGED_V1 = ResourceUrlChanged(
        ANY_UUID,
        1,
        TIME_NOW.minusSeconds(2),
        ANY_RESOURCE_URL.capitalize()
)

internal val EVENT_RESOURCE_URL_CHANGED_V2 = ResourceUrlChanged(
        ANY_UUID,
        2,
        TIME_NOW,
        ANY_OTHER_RESOURCE_URL
)

internal val ONE_EVENT_HISTORY = listOf(EVENT_ART_CREATED)
internal val TWO_EVENTS_HISTORY = listOf(EVENT_ART_CREATED, EVENT_RESOURCE_URL_CHANGED_V1)
internal val THREE_EVENTS_HISTORY = listOf(EVENT_ART_CREATED, EVENT_RESOURCE_URL_CHANGED_V1, EVENT_RESOURCE_URL_CHANGED_V2)

internal fun assertStatesEquals(expected: Art, replayed: Art) {
    Assertions.assertEquals(expected.uuid(), replayed.uuid())
    Assertions.assertEquals(expected.version(), replayed.version())
    Assertions.assertEquals(expected.author(), replayed.author())
    Assertions.assertEquals(expected.resourceUrl(), replayed.resourceUrl())
    Assertions.assertArrayEquals(replayed.history().toTypedArray(), expected.history().toTypedArray())
}
