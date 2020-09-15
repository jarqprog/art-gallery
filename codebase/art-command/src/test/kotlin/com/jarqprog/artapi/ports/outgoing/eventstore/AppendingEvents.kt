package com.jarqprog.artapi.ports.outgoing.eventstore

import com.jarqprog.artapi.domain.art.ProcessingResult
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStoreTestUtils.allSecondHistoryProcessingResults
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStoreTestUtils.allFirstHistoryProcessingResults
import com.jarqprog.artapi.support.EventContainer.ANY_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.ANY_OTHER_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.EVENT_ART_CREATED
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V1
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V4
import com.jarqprog.artapi.support.InMemoryEventStreamRepository
import com.jarqprog.artapi.support.InMemorySnapshotRepository
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V0
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V1
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V4

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import java.util.concurrent.ConcurrentHashMap

internal class AppendingEvents {

    private lateinit var eventStreamRepository: EventStreamRepository
    private lateinit var snapshotDatabase: SnapshotRepository
    private lateinit var eventStore: EventStore

    @BeforeEach
    fun prepareStorage() {
        eventStreamRepository = InMemoryEventStreamRepository(ConcurrentHashMap())
        snapshotDatabase = InMemorySnapshotRepository(ConcurrentHashMap())
        eventStore = EventStorage(eventStreamRepository, snapshotDatabase)
    }

    @Test
    fun `should append proper event to existing history`() {
        // given
        eventStore.save(ProcessingResult(EVENT_ART_CREATED, SNAPSHOT_V0)).subscribe()
        val processingResult = ProcessingResult(EVENT_RESOURCE_URL_CHANGED_V1, SNAPSHOT_V1)

        // when
        val result = eventStore.save(processingResult)

        // then
        StepVerifier.create(result)
                .verifyComplete()

        StepVerifier.create(eventStreamRepository.streamVersion(ANY_IDENTIFIER))
                .expectNext(SNAPSHOT_V1.version())
                .verifyComplete()
    }

    @Test
    fun `should return failure on appending event with incorrect version`() {
        // given
        eventStore.save(ProcessingResult(EVENT_ART_CREATED, SNAPSHOT_V0)).subscribe()
        val expectedStreamVersion = EVENT_ART_CREATED.version()

        // when
        val result = eventStore.save(ProcessingResult(EVENT_RESOURCE_URL_CHANGED_V4, SNAPSHOT_V4))

        // then
        StepVerifier.create(result)
                .expectError()
                .verify()

        StepVerifier.create(eventStreamRepository.streamVersion(ANY_IDENTIFIER))
                .expectNext(expectedStreamVersion)
                .verifyComplete()
    }

    @Test
    fun `should save all events`() {
        // given
        val resultsToBeSaved = allFirstHistoryProcessingResults()

        // when
        resultsToBeSaved.forEach { result -> eventStore.save(result).subscribe() }

        // then
        StepVerifier.create(eventStreamRepository.streamVersion(ANY_IDENTIFIER))
                .expectNext(resultsToBeSaved.last().artEvent.version())
                .verifyComplete()
    }

    @Test
    fun `should return failure on saving same result two times`() {
        // given
        eventStore.save(ProcessingResult(EVENT_ART_CREATED, SNAPSHOT_V0)).subscribe()
        val processingResult = ProcessingResult(EVENT_RESOURCE_URL_CHANGED_V1, SNAPSHOT_V1)
        eventStore.save(processingResult).subscribe()

        // when
        val result = eventStore.save(processingResult)

        // then
        StepVerifier.create(result)
                .expectError()
                .verify()
    }

    @Test
    fun `should store two histories`() {
        // given
        val firstHistory = allFirstHistoryProcessingResults()
        val secondHistory = allSecondHistoryProcessingResults()
        val merged = firstHistory.plus(secondHistory)

        // when
        merged.sortedBy { result -> result.artEvent.timestamp() }
                .forEach { result -> eventStore.save(result).subscribe() }

        // then
        StepVerifier.create(eventStreamRepository.streamVersion(ANY_IDENTIFIER))
                .expectNext(firstHistory.last().artEvent.version())
                .verifyComplete()

        StepVerifier.create(eventStreamRepository.streamVersion(ANY_OTHER_IDENTIFIER))
                .expectNext(secondHistory.last().artEvent.version())
                .verifyComplete()
    }
}
