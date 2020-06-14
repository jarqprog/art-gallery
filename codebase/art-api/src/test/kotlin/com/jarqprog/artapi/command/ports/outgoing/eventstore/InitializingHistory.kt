package com.jarqprog.artapi.command.ports.outgoing.eventstore


import com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.inmemory.InMemoryEventStreamDatabase
import com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.inmemory.InMemorySnapshotDatabase
import com.jarqprog.artapi.domain.*
import com.jarqprog.artapi.domain.EVENT_ART_CREATED
import com.jarqprog.artapi.domain.EVENT_RESOURCE_URL_CHANGED_V1
import com.jarqprog.artapi.domain.TIME_NOW
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.vo.Author
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.domain.vo.User

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import java.util.concurrent.ConcurrentHashMap


internal class InitializingHistory {

    val artId = EVENT_ART_CREATED.artId()

    private lateinit var eventStreamDatabase: EventStreamDatabase
    private lateinit var snapshotDatabase: SnapshotDatabase
    private lateinit var eventStore: EventStore

    @BeforeEach
    fun prepareStorage() {

        eventStreamDatabase = InMemoryEventStreamDatabase(ConcurrentHashMap())
        snapshotDatabase = InMemorySnapshotDatabase(ConcurrentHashMap())

        eventStore = EventStorage(eventStreamDatabase, snapshotDatabase)
    }

    @Test
    fun `should initialize history with art created event`() {

        // given
        val eventThatShouldInitializeStream = EVENT_ART_CREATED

        // when
        val result = eventStore.save(eventThatShouldInitializeStream)

        // then
        StepVerifier.create(result)
                .verifyComplete()

        StepVerifier.create(eventStreamDatabase.historyExistsById(artId))
                .expectNext(true)
                .verifyComplete()
    }

    @Test
    fun `should return failure on initializing history if history for the same art identifier already exists`() {

        // given
        eventStore.save(EVENT_ART_CREATED).subscribe()
        val eventShouldNotBeSaved = ArtCreated(
                artId,
                0,
                TIME_NOW.minusSeconds(1),
                Author("Tom"),
                Resource("/anyPath"),
                User("Tom"),
                ArtGenre.UNDEFINED,
                ArtStatus.ACTIVE
        )

        // when
        val result = eventStore.save(eventShouldNotBeSaved)

        // then
        StepVerifier.create(result)
                .expectError()
                .verify()
    }

    @Test
    fun `should return failure on initializing history if event has incorrect version`() {

        // given
        val incorrectVersion = 1
        val eventShouldNotBeSaved = ArtCreated(
                EVENT_ART_CREATED.artId(),
                incorrectVersion,
                TIME_NOW.minusSeconds(1),
                Author("Tom"),
                Resource("/anyPath"),
                User("Tom"),
                ArtGenre.UNDEFINED,
                ArtStatus.ACTIVE
        )

        // when
        val result = eventStore.save(eventShouldNotBeSaved)

        // then
        StepVerifier.create(result)
                .expectError()
                .verify()
    }

    @Test
    fun `should not initialize history when event is not art created`() {

        // given
        val shouldNotInitializeStream = EVENT_RESOURCE_URL_CHANGED_V1

        // when
        val result = eventStore.save(shouldNotInitializeStream)

        // then
        StepVerifier.create(result)
                .expectError()
                .verify()

        StepVerifier.create(eventStreamDatabase.historyExistsById(shouldNotInitializeStream.artId()))
                .expectNext(false)
                .verifyComplete()
    }
}