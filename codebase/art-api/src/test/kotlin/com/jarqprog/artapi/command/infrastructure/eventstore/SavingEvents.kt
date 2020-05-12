package com.jarqprog.artapi.command.infrastructure.eventstore


import com.jarqprog.artapi.command.artdomain.EventStore
import com.jarqprog.artapi.command.EVENT_ART_CREATED
import com.jarqprog.artapi.command.EVENT_RESOURCE_URL_CHANGED_V1
import com.jarqprog.artapi.command.EVENT_RESOURCE_URL_CHANGED_V2
import com.jarqprog.artapi.command.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.command.infrastructure.eventstore.exceptions.EventStoreFailure
import com.jarqprog.artapi.command.infrastructure.eventstore.inmemory.InMemoryEventStreamDatabase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.util.*
import java.util.concurrent.ConcurrentHashMap

internal class SavingEvents {

    private lateinit var eventStreamDatabase: EventStreamDatabase
    private lateinit var eventStore: EventStore

    @BeforeEach
    fun prepareStorage() {
        eventStreamDatabase = InMemoryEventStreamDatabase(ConcurrentHashMap())
        eventStore = EventStorage(eventStreamDatabase)
    }

    @Test
    fun shouldNotReturnFailureWhenSavingProperEvent() {

        val eventThatCanInitializeHistory = EVENT_ART_CREATED
        val result = eventStore.save(eventThatCanInitializeHistory)

        assertTrue(result.isEmpty)
        assertTrue(eventStreamDatabase.historyExistsById(eventThatCanInitializeHistory.artId()))
    }

    @Test
    fun shouldReturnFailureOnSavingEventIfThereIsNoHistory() {

        val eventWithoutHistory = EVENT_RESOURCE_URL_CHANGED_V1

        val result = eventStore.save(eventWithoutHistory)

        assertTrue(result.isPresent)
        assertFalse(eventStreamDatabase.historyExistsById(eventWithoutHistory.artId()))
    }

    @Test
    fun shouldReturnFailureOnSavingEventWithIncorrectVersion() {

        val eventThatCanInitializeHistory = EVENT_ART_CREATED
        val eventWithIncorrectVersion = EVENT_RESOURCE_URL_CHANGED_V2

        val shouldBeEmpty = eventStore.save(eventThatCanInitializeHistory)
        val shouldContainFailure = eventStore.save(eventWithIncorrectVersion)

        assertTrue(shouldBeEmpty.isEmpty)
        assertTrue(shouldContainFailure.isPresent)
    }

    @Test
    fun shouldSaveAllEvents() {

        val firstEvent = EVENT_ART_CREATED
        val secondEvent = EVENT_RESOURCE_URL_CHANGED_V1
        val thirdEvent = EVENT_RESOURCE_URL_CHANGED_V2

        val firstResult = eventStore.save(firstEvent)
        val secondResult = eventStore.save(secondEvent)
        val thirdResult = eventStore.save(thirdEvent)

        assertTrue(firstResult.isEmpty)
        assertTrue(secondResult.isEmpty)
        assertTrue(thirdResult.isEmpty)

        val optionalHistory = eventStreamDatabase.load(firstEvent.artId())
        assertTrue(optionalHistory.isPresent)

        assertHistoryDescriptorShouldMatchWithEvents(optionalHistory.get(), HISTORY_WITH_THREE_EVENTS.events())
    }

    @Test
    fun shouldReturnFailureOnSavingSameEventTwoTimes() {

        val firstEvent = EVENT_ART_CREATED
        val secondEvent = EVENT_RESOURCE_URL_CHANGED_V1
        val thirdEvent = EVENT_RESOURCE_URL_CHANGED_V2

        val firstResult = eventStore.save(firstEvent)
        val secondResult = eventStore.save(secondEvent)
        val thirdResult = eventStore.save(secondEvent)
        val fourthResult = eventStore.save(thirdEvent)

        assertTrue(firstResult.isEmpty)
        assertTrue(secondResult.isEmpty)
        assertTrue(thirdResult.isPresent)
        assertTrue(fourthResult.isEmpty)

        val optionalHistory = eventStreamDatabase.load(firstEvent.artId())
        assertTrue(optionalHistory.isPresent)

        assertHistoryDescriptorShouldMatchWithEvents(optionalHistory.get(), HISTORY_WITH_THREE_EVENTS.events())
    }

    @Test
    fun shouldStoreTwoEventStreams() {

        val results = mutableListOf<Optional<EventStoreFailure>>()

        HISTORY_WITH_THREE_EVENTS.events().plus(ANOTHER_HISTORY.events())
                .forEach { event ->
                    results.add(eventStore.save(event))
                }

        val firstOptionalHistory = eventStreamDatabase.load(HISTORY_WITH_THREE_EVENTS.artId())
        val secondOptionalHistory = eventStreamDatabase.load(ANOTHER_HISTORY.artId())

        assertAll(
                {
                    results.forEach { result ->
                        assertTrue(result.isEmpty)
                    }
                },
                { assertTrue(firstOptionalHistory.isPresent) },
                { assertTrue(secondOptionalHistory.isPresent) }
        )

        assertHistoryDescriptorShouldMatchWithEvents(firstOptionalHistory.get(), HISTORY_WITH_THREE_EVENTS.events())
        assertHistoryDescriptorShouldMatchWithEvents(secondOptionalHistory.get(), ANOTHER_HISTORY.events())
    }
}