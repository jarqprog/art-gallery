package com.jarqprog.artapi.support

import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.events.ArtEvent
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertAll
import java.util.Comparator

object ArtTestAssertions {

    fun assertStatesEquals(expected: TestArt, replayed: ArtAggregate) {

        assertAll("art states should be equal",
                { Assertions.assertEquals(expected.identifier, replayed.identifier()) },
                { Assertions.assertEquals(expected.version, replayed.version()) },
                { Assertions.assertEquals(expected.timestamp, replayed.timestamp()) },
                { Assertions.assertEquals(expected.author, replayed.author()) },
                { Assertions.assertEquals(expected.resource, replayed.resource()) },
                { Assertions.assertEquals(expected.addedBy, replayed.addedBy()) },
                { Assertions.assertEquals(expected.genre, replayed.genre()) },
                { Assertions.assertEquals(expected.status, replayed.status()) }
        )
    }

    fun assertStatesEquals(expected: ArtAggregate, replayed: ArtAggregate) {

        assertAll("art states should be equal",
                { Assertions.assertEquals(expected.identifier(), replayed.identifier()) },
                { Assertions.assertEquals(expected.version(), replayed.version()) },
                { Assertions.assertEquals(expected.timestamp(), replayed.timestamp()) },
                { Assertions.assertEquals(expected.author(), replayed.author()) },
                { Assertions.assertEquals(expected.resource(), replayed.resource()) },
                { Assertions.assertEquals(expected.addedBy(), replayed.addedBy()) },
                { Assertions.assertEquals(expected.genre(), replayed.genre()) },
                { Assertions.assertEquals(expected.status(), replayed.status()) }
        )
    }

    fun assertArtAndHistoryValuesMatch(art: ArtAggregate, history: ArtHistory) {

        assertAll("art and history values should match",
                { Assertions.assertEquals(art.identifier(), history.artId()) },
                { Assertions.assertEquals(art.version(), history.version()) },
                { Assertions.assertEquals(art.timestamp(), history.timestamp()) }
        )
    }

    fun assertHistoryAndEventsValuesMatch(events: List<ArtEvent>, history: ArtHistory) {

        val latestEvent = events.sortedWith(Comparator.comparing(ArtEvent::timestamp)).last()

        assertAll("history and event values should match",
                { Assertions.assertEquals(latestEvent.artId(), history.artId()) },
                { Assertions.assertEquals(latestEvent.version(), history.version()) },
                { Assertions.assertEquals(latestEvent.timestamp(), history.timestamp()) },
                { Assertions.assertArrayEquals(events.toTypedArray(), history.events().toTypedArray()) }
        )
    }
}
