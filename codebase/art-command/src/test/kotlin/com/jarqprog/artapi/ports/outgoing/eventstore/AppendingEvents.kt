package com.jarqprog.artapi.ports.outgoing.eventstore


import com.jarqprog.artapi.ports.outgoing.eventstore.dao.inmemory.InMemoryEventStreamDatabase
import com.jarqprog.artapi.ports.outgoing.eventstore.dao.inmemory.InMemorySnapshotDatabase
//import com.jarqprog.artapi.domain.EventSupport.ANOTHER_EVENT_ART_CREATED
//import com.jarqprog.artapi.domain.EventSupport.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1
//import com.jarqprog.artapi.domain.EventSupport.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2
//import com.jarqprog.artapi.domain.EventSupport.ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3
//import com.jarqprog.artapi.domain.EventSupport.EVENT_ART_CREATED
//import com.jarqprog.artapi.domain.EventSupport.EVENT_RESOURCE_URL_CHANGED_V1
//import com.jarqprog.artapi.domain.EventSupport.EVENT_RESOURCE_URL_CHANGED_V2
//import com.jarqprog.artapi.domain.EventSupport.EVENT_RESOURCE_URL_CHANGED_V3
//import com.jarqprog.artapi.domain.EventSupport.EVENT_RESOURCE_URL_CHANGED_V4
import com.jarqprog.artapi.domain.events.ArtEvent
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import java.util.Comparator
import java.util.concurrent.ConcurrentHashMap


internal class AppendingEvents {

//    private val historyIdentifier = EVENT_ART_CREATED.artId()
//    private lateinit var eventStreamDatabase: EventStreamDatabase
//    private lateinit var snapshotDatabase: SnapshotDatabase
//    private lateinit var eventStore: EventStore
//
//    @BeforeEach
//    fun prepareStorage() {
//        eventStreamDatabase = InMemoryEventStreamDatabase(ConcurrentHashMap())
//        snapshotDatabase = InMemorySnapshotDatabase(ConcurrentHashMap())
//
//        eventStore = EventStorage(eventStreamDatabase, snapshotDatabase)
//
//        eventStore.save(EVENT_ART_CREATED).subscribe()
//    }
//
//    @Test
//    fun `should append proper event to existing history`() {
//
//        // given
//        val eventToAppend = EVENT_RESOURCE_URL_CHANGED_V1
//
//        // when
//        val result = eventStore.save(eventToAppend)
//
//        // then
//        StepVerifier.create(result)
//                .verifyComplete()
//
//        StepVerifier.create(eventStreamDatabase.streamVersion(historyIdentifier))
//                .expectNext(eventToAppend.version())
//                .verifyComplete()
//    }
//
//    @Test
//    fun `should return failure on appending event with incorrect version`() {
//
//        // given
//        val shouldNotBeAppended = EVENT_RESOURCE_URL_CHANGED_V4
//        val expectedStreamVersion = EVENT_ART_CREATED.version()
//
//        // when
//        val result = eventStore.save(shouldNotBeAppended)
//
//        // then
//        StepVerifier.create(result)
//                .expectError()
//                .verify()
//
//        StepVerifier.create(eventStreamDatabase.streamVersion(historyIdentifier))
//                .expectNext(expectedStreamVersion)
//                .verifyComplete()
//    }
//
//    @Test
//    fun `should save all events`() {
//
//        // given
//        val eventsToBeSaved = listOf(
//                EVENT_RESOURCE_URL_CHANGED_V1,
//                EVENT_RESOURCE_URL_CHANGED_V2,
//                EVENT_RESOURCE_URL_CHANGED_V3,
//                EVENT_RESOURCE_URL_CHANGED_V4
//        )
//
//        // when
//        eventsToBeSaved.forEach { event -> eventStore.save(event).subscribe() }
//
//        // then
//        StepVerifier.create(eventStreamDatabase.streamVersion(historyIdentifier))
//                .expectNext(EVENT_RESOURCE_URL_CHANGED_V4.version())
//                .verifyComplete()
//    }
//
//    @Test
//    fun `should return failure on saving same event two times`() {
//
//        // given
//        val event = EVENT_RESOURCE_URL_CHANGED_V1
//        eventStore.save(event).subscribe()
//
//        // when
//        val result = eventStore.save(event)
//
//        // then
//        StepVerifier.create(result)
//                .expectError()
//                .verify()
//    }
//
//    @Test
//    fun `should store two histories`() {
//
//        // given
//        val firstHistory = listOf(
//                EVENT_RESOURCE_URL_CHANGED_V1,
//                EVENT_RESOURCE_URL_CHANGED_V2,
//                EVENT_RESOURCE_URL_CHANGED_V3,
//                EVENT_RESOURCE_URL_CHANGED_V4
//        )
//
//        val secondHistoryIdentifier = ANOTHER_EVENT_ART_CREATED.artId()
//        eventStore.save(ANOTHER_EVENT_ART_CREATED).subscribe()
//        val secondHistory = listOf(
//                ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1,
//                ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2,
//                ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3
//        )
//
//        val merged = firstHistory.plus(secondHistory).sortedWith(Comparator.comparing(ArtEvent::timestamp))
//
//        // when
//        merged.forEach { event -> eventStore.save(event).subscribe() }
//
//        // then
//        StepVerifier.create(eventStreamDatabase.streamVersion(historyIdentifier))
//                .expectNext(firstHistory.last().version())
//                .verifyComplete()
//
//        StepVerifier.create(eventStreamDatabase.streamVersion(secondHistoryIdentifier))
//                .expectNext(secondHistory.last().version())
//                .verifyComplete()
//    }
}