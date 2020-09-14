package com.jarqprog.artapi.support

import com.jarqprog.artapi.domain.events.ArtCreated
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertAll

object EventAssertions {

    fun assertArtCreatedEventsEquals(expected: ArtCreated, event: ArtCreated) {

        assertAll("art created events should be equal - without comparing timestamps",
                { Assertions.assertEquals(expected.name(), event.name()) },
                { Assertions.assertEquals(expected.artId(), event.artId()) },
                { Assertions.assertEquals(expected.version(), event.version()) },
                { Assertions.assertEquals(expected.author(), event.author()) },
                { Assertions.assertEquals(expected.resource(), event.resource()) },
                { Assertions.assertEquals(expected.addedBy(), event.addedBy()) },
                { Assertions.assertEquals(expected.artGenre(), event.artGenre()) },
                { Assertions.assertEquals(expected.artStatus(), event.artStatus()) }
        )
    }
}
