package com.jarqprog.artapi.ports.outgoing.eventstore


import com.jarqprog.artapi.domain.ArtStatus
import com.jarqprog.artapi.domain.ArtGenre
import com.jarqprog.artapi.ports.outgoing.eventstore.dao.inmemory.InMemoryEventStreamDatabase
import com.jarqprog.artapi.ports.outgoing.eventstore.dao.inmemory.InMemorySnapshotDatabase
import com.jarqprog.artapi.applicationservice.commands.CreateArt
import com.jarqprog.artapi.domain.vo.Author
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.domain.vo.User
import com.jarqprog.artapi.support.EventContainer.EVENT_ART_CREATED
import com.jarqprog.artapi.support.EventContainer.EVENT_RESOURCE_URL_CHANGED_V1

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import java.util.concurrent.ConcurrentHashMap


internal class InitializingHistory {

    private val artId = EVENT_ART_CREATED.artId()

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

        val commandWithDuplicatedArtId = CreateArt(
                EVENT_ART_CREATED.artId(),
                Author("Tom"),
                Resource("/anyPath"),
                User("Tom"),
                ArtGenre.UNDEFINED,
                ArtStatus.ACTIVE
        )

        val eventShouldNotBeSaved = commandWithDuplicatedArtId.asEvent()

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