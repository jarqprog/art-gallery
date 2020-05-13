package com.jarqprog.artapi.command.ports.outgoing.eventstore

import com.jarqprog.artapi.command.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.command.NOT_USED_HISTORY_ID
import com.jarqprog.artapi.command.ports.outgoing.EventStore
import com.jarqprog.artapi.command.ports.outgoing.eventstore.inmemory.InMemoryEventStreamDatabase
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.ConcurrentHashMap

internal class LoadingEvents {

    private lateinit var eventStreamDatabase: EventStreamDatabase
    private lateinit var eventStore: EventStore

    @BeforeEach
    fun prepareStorage() {
        eventStreamDatabase = InMemoryEventStreamDatabase(ConcurrentHashMap())
        eventStore = EventStorage(eventStreamDatabase)
        HISTORY_WITH_THREE_EVENTS.events().plus(ANOTHER_HISTORY.events())
                .forEach { event -> eventStore.save(event) }
    }

    @Test
    fun shouldNotLoadNotExistingHistory() {

        val shouldBeEmpty = eventStore.load(NOT_USED_HISTORY_ID)

        assertTrue(shouldBeEmpty.isEmpty)
    }

    @Test
    fun shouldLoadAllHistories() {

        val firstFetchedHistory = eventStore.load(HISTORY_WITH_THREE_EVENTS.artId()).get()
        val secondFetchedHistory = eventStore.load(ANOTHER_HISTORY.artId()).get()

        assertHistoriesAreTheSame(firstFetchedHistory, HISTORY_WITH_THREE_EVENTS)
        assertHistoriesAreTheSame(secondFetchedHistory, ANOTHER_HISTORY)
    }

    @Test
    fun shouldLoadAllEventsFromGivenPointInThePast() {

        val identifier = ANOTHER_HISTORY.artId()

        val firstPointInTime = ANOTHER_HISTORY.events().first().timestamp().plusSeconds(1)
        val secondPointInTime = ANOTHER_HISTORY.events().last().timestamp().minusSeconds(1)

        val firstFetchedHistory = eventStore.load(identifier, firstPointInTime).get()
        val secondFetchedHistory = eventStore.load(identifier, secondPointInTime).get()

        val firstExpectedHistory = filterHistoryByPointInTime(ANOTHER_HISTORY, firstPointInTime)
        val secondExpectedHistory = filterHistoryByPointInTime(ANOTHER_HISTORY, secondPointInTime)

        assertHistoriesAreTheSame(firstFetchedHistory, firstExpectedHistory)
        assertHistoriesAreTheSame(secondFetchedHistory, secondExpectedHistory)
    }
}