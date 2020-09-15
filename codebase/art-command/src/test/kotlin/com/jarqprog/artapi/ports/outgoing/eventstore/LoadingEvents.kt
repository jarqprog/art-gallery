package com.jarqprog.artapi.ports.outgoing.eventstore

import com.jarqprog.artapi.domain.art.ArtHistory
import com.jarqprog.artapi.domain.art.ArtTestUtils.eventsToDescriptors
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStoreTestUtils.allHistoriesProcessingResultsSorted
import com.jarqprog.artapi.support.EventContainer.ANY_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.ANY_OTHER_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.NOT_EXISTING_IDENTIFIER
import com.jarqprog.artapi.support.HistoryContainer.COMPLETE_FIRST_HISTORY
import com.jarqprog.artapi.support.HistoryContainer.COMPLETE_SECOND_HISTORY
import com.jarqprog.artapi.support.InMemoryEventStreamRepository
import com.jarqprog.artapi.support.InMemorySnapshotRepository

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import java.util.concurrent.ConcurrentHashMap

class LoadingEvents {

    private lateinit var eventStreamRepository: EventStreamRepository
    private lateinit var snapshotDatabase: SnapshotRepository
    private lateinit var eventStore: EventStore

    @BeforeEach
    fun prepareStorage() {
        eventStreamRepository = InMemoryEventStreamRepository(ConcurrentHashMap())
        snapshotDatabase = InMemorySnapshotRepository(ConcurrentHashMap())
        eventStore = EventStorage(eventStreamRepository, snapshotDatabase)

        allHistoriesProcessingResultsSorted().forEach { event -> eventStore.save(event).subscribe() }
    }

    @Test
    fun `should return empty result on loading not existing history`() {
        // when
        val result = eventStore.load(NOT_EXISTING_IDENTIFIER)

        // then
        StepVerifier.create(result)
                .verifyComplete()
    }

    @Test
    fun `should load all histories with proper values`() {
        // when
        val firstResult = eventStore.load(ANY_IDENTIFIER)
        val secondResult = eventStore.load(ANY_OTHER_IDENTIFIER)

        // then
        StepVerifier.create(firstResult)
                .expectNext(COMPLETE_FIRST_HISTORY)
                .verifyComplete()

        StepVerifier.create(secondResult)
                .expectNext(COMPLETE_SECOND_HISTORY)
                .verifyComplete()
    }

    @Test
    fun `should load history for given point in time`() {
        // given
        val middleIndex = COMPLETE_FIRST_HISTORY.events().size / 2
        val firstPartOfHistory = COMPLETE_FIRST_HISTORY.events().subList(0, middleIndex)
        val eventToExclude = firstPartOfHistory.last()
        val pointInTime = eventToExclude.timestamp().minusSeconds(1)
        val expectedResult = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(firstPartOfHistory.minusElement(eventToExclude)))

        // when
        val result = eventStore.load(ANY_IDENTIFIER, pointInTime)

        // then
        StepVerifier.create(result)
                .expectNext(expectedResult)
                .verifyComplete()
    }

    @Test
    fun `should load history with all events when passed future point in time`() {
        // given
        val pointInTime = COMPLETE_FIRST_HISTORY.timestamp().plusSeconds(3_600)
        val expectedResult = COMPLETE_FIRST_HISTORY

        // when
        val result = eventStore.load(ANY_IDENTIFIER, pointInTime)

        // then
        StepVerifier.create(result)
                .expectNext(expectedResult)
                .verifyComplete()
    }

    @Test
    fun `should load empty history when passed point in time earlier then first event timestamp`() {
        // given
        val pointInTime = COMPLETE_FIRST_HISTORY.events().first().timestamp().minusSeconds(3600)
        val expectedResult = ArtHistory.with(ANY_IDENTIFIER)

        // when
        val result = eventStore.load(ANY_IDENTIFIER, pointInTime)

        // then
        StepVerifier.create(result)
                .expectNext(expectedResult)
                .verifyComplete()
    }
}