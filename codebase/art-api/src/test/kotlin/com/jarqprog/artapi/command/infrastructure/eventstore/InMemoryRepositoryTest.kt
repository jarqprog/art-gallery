package com.jarqprog.artapi.command.infrastructure.eventstore


import com.jarqprog.artapi.command.artdomain.artfactory.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.command.artdomain.events.ResourceChanged
import com.jarqprog.artapi.command.artdomain.vo.Resource
import com.jarqprog.artapi.command.infrastructure.eventstore.exceptions.EventStoreFailure
import com.jarqprog.artapi.command.infrastructure.eventstore.inmemory.InMemoryStorage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant

//internal class InMemoryRepositoryTest {
//
//    @Test
//    fun shouldSaveAllEvents() {
//
//        val repository = InMemoryStorage()
//
//        HISTORY_WITH_THREE_EVENTS.forEach(repository::save)
//
//        val events = repository.load(HISTORY_WITH_THREE_EVENTS[0].artId())
//
//        assertArrayEquals(HISTORY_WITH_THREE_EVENTS.toTypedArray(), events.toTypedArray())
//    }
//
//    @Test
//    fun shouldThrowConcurrentExceptionAndNotPersistEvent() {
//
//        val repository = InMemoryStorage()
//        HISTORY_WITH_THREE_EVENTS.forEach(repository::save)
//
//        val alreadyPersistedEvent = HISTORY_WITH_THREE_EVENTS.last()
//        val artId = alreadyPersistedEvent.artId()
//        val versionAlreadyPersisted = alreadyPersistedEvent.version()
//
//        val thatEventShouldNotBePersisted = ResourceChanged(
//                artId,
//                versionAlreadyPersisted,
//                Instant.now(),
//                Resource("anyPath-anyValue")
//        )
//
//        assertThrows(EventStoreFailure::class.java) { repository.save(thatEventShouldNotBePersisted) }
//
//        val events = repository.load(artId)
//
//        assertArrayEquals(HISTORY_WITH_THREE_EVENTS.toTypedArray(), events.toTypedArray())
//    }
//}