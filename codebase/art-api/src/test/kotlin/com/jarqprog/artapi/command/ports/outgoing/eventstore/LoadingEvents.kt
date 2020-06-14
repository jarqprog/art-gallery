package com.jarqprog.artapi.command.ports.outgoing.eventstore

import com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.inmemory.InMemoryEventStreamDatabase
import com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.inmemory.InMemorySnapshotDatabase
import com.jarqprog.artapi.domain.*
import com.jarqprog.artapi.domain.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.domain.NOT_USED_HISTORY_ID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import java.util.concurrent.ConcurrentHashMap

internal class LoadingEvents {

    private lateinit var eventStreamDatabase: EventStreamDatabase
    private lateinit var snapshotDatabase: SnapshotDatabase
    private lateinit var eventStore: EventStore

    @BeforeEach
    fun prepareStorage() {
        eventStreamDatabase = InMemoryEventStreamDatabase(ConcurrentHashMap())
        snapshotDatabase = InMemorySnapshotDatabase(ConcurrentHashMap())

        eventStore = EventStorage(eventStreamDatabase, snapshotDatabase)

        HISTORY_WITH_THREE_EVENTS.events().plus(ANOTHER_HISTORY.events())
                .forEach { event -> eventStore.save(event).subscribe() }
    }

    @Test
    fun `should return empty result on loading not existing history`() {

        // given
        val notExistingHistoryId = NOT_USED_HISTORY_ID

        // when
        val result = eventStore.load(notExistingHistoryId)

        // then
        StepVerifier.create(result)
                .verifyComplete()
    }

    @Test
    fun `should load all histories with proper values`() {

        // given
        val firstHistoryId = HISTORY_WITH_THREE_EVENTS.artId()
        val secondHistoryId = ANOTHER_HISTORY.artId()

        // when
        val firstResult = eventStore.load(firstHistoryId)
        val secondResult = eventStore.load(secondHistoryId)

        // then
        StepVerifier.create(firstResult)
                .expectNext(HISTORY_WITH_THREE_EVENTS)
                .verifyComplete()

        StepVerifier.create(secondResult)
                .expectNext(ANOTHER_HISTORY)
                .verifyComplete()
    }

    @Test
    fun `should load history for given point in time`() {

        // given
        val historyId = HISTORY_WITH_THREE_EVENTS.artId()
        val eventToExclude = HISTORY_WITH_THREE_EVENTS.events().last()
        val pointInTime = eventToExclude.timestamp().minusSeconds(1)
        val expectedHistory = ArtHistory.withEvents(HISTORY_WITH_THREE_EVENTS.events().minusElement(eventToExclude))

        // when
        val result = eventStore.load(historyId, pointInTime)

        // then
        StepVerifier.create(result)
                .expectNext(expectedHistory)
                .verifyComplete()
    }

    @Test
    fun `should load history with all events when passed future point in time`() {

        // given
        val historyId = HISTORY_WITH_THREE_EVENTS.artId()
        val pointInTime = HISTORY_WITH_THREE_EVENTS.events().last().timestamp().plusSeconds(3600)

        // when
        val result = eventStore.load(historyId, pointInTime)

        // then
        StepVerifier.create(result)
                .expectNext(HISTORY_WITH_THREE_EVENTS)
                .verifyComplete()
    }

    @Test
    fun `should load empty history when passed point in time earlier then first event timestamp`() {

        // given
        val historyId = HISTORY_WITH_THREE_EVENTS.artId()
        val pointInTime = HISTORY_WITH_THREE_EVENTS.events().first().timestamp().minusSeconds(3600)
        val expected = ArtHistory.initialize(historyId)

        // when
        val result = eventStore.load(historyId, pointInTime)

        // then
        StepVerifier.create(result)
                .expectNext(expected)
                .verifyComplete()
    }
}