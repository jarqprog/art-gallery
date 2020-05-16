package com.jarqprog.artapi.command.ports.outgoing.eventstore

import arrow.core.getOrHandle
import com.jarqprog.artapi.command.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.command.NOT_USED_HISTORY_ID
import com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.inmemory.InMemoryEventStreamDatabase
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
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

        eventStore.load(NOT_USED_HISTORY_ID)
                .map { shouldBeEmpty -> assertTrue(shouldBeEmpty.isEmpty) }
                .getOrHandle { fail("has failure") }
    }

    @Test
    fun shouldLoadAllHistories() {

        eventStore.load(HISTORY_WITH_THREE_EVENTS.artId())
                .map { optionalHistory ->
                    optionalHistory
                            .map { history -> assertHistoriesAreTheSame(history, HISTORY_WITH_THREE_EVENTS) }
                            .orElseGet { fail("was empty") }
                }
                .getOrHandle { fail("has failure") }

        eventStore.load(ANOTHER_HISTORY.artId())
                .map { optionalHistory ->
                    optionalHistory
                            .map { history -> assertHistoriesAreTheSame(history, ANOTHER_HISTORY) }
                            .orElseGet { fail("was empty") }
                }
                .getOrHandle { fail("has failure") }
    }

    @Test
    fun shouldLoadAllEventsFromGivenPointInThePast() {

        val identifier = ANOTHER_HISTORY.artId()

        val firstPointInTime = ANOTHER_HISTORY.events().first().timestamp().plusSeconds(1)
        val firstExpectedHistory = filterHistoryByPointInTime(ANOTHER_HISTORY, firstPointInTime)
        eventStore.load(identifier, firstPointInTime)
                .map { optionalHistory ->
                    optionalHistory
                            .map { history -> assertHistoriesAreTheSame(history, firstExpectedHistory) }
                            .orElseGet { fail("was empty") }
                }
                .getOrHandle { fail("has failure") }

        val secondPointInTime = ANOTHER_HISTORY.events().last().timestamp().minusSeconds(1)
        val secondExpectedHistory = filterHistoryByPointInTime(ANOTHER_HISTORY, secondPointInTime)
        eventStore.load(identifier, secondPointInTime)
                .map { optionalHistory ->
                    optionalHistory
                            .map { history -> assertHistoriesAreTheSame(history, secondExpectedHistory) }
                            .orElseGet { fail("was empty") }
                }
                .getOrHandle { fail("has failure") }
    }
}