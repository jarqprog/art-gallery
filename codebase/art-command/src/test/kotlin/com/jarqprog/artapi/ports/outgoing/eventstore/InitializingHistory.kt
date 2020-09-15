package com.jarqprog.artapi.ports.outgoing.eventstore

import com.jarqprog.artapi.applicationservice.ProcessingResult
import com.jarqprog.artapi.support.EventContainer.ANY_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.EVENT_ART_CREATED
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V1
import com.jarqprog.artapi.support.InMemoryEventStreamRepository
import com.jarqprog.artapi.support.InMemorySnapshotRepository
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V0
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V1

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import java.util.concurrent.ConcurrentHashMap

internal class InitializingHistory {

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
    fun `should initialize history with art created event`() {
        // given
        val processingResult = ProcessingResult(EVENT_ART_CREATED, SNAPSHOT_V0)

        // when
        val result = eventStore.save(processingResult)

        // then
        StepVerifier.create(result)
                .verifyComplete()

        StepVerifier.create(eventStreamRepository.historyExistsById(ANY_IDENTIFIER))
                .expectNext(true)
                .verifyComplete()
    }

    @Test
    fun `should return failure on initializing history if history for the same art identifier already exists`() {
        // given
        val processingResult = ProcessingResult(EVENT_ART_CREATED, SNAPSHOT_V0)
        eventStore.save(processingResult).subscribe()

        // when
        val result = eventStore.save(processingResult)

        // then
        StepVerifier.create(result)
                .expectError()
                .verify()
    }

    @Test
    fun `should not initialize history when event is not art created`() {
        // given
        val processingResult = ProcessingResult(EVENT_RESOURCE_URL_CHANGED_V1, SNAPSHOT_V1)

        // when
        val result = eventStore.save(processingResult)

        // then
        StepVerifier.create(result)
                .expectError()
                .verify()

        StepVerifier.create(eventStreamRepository.historyExistsById(ANY_IDENTIFIER))
                .expectNext(false)
                .verifyComplete()
    }
}
