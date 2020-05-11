package com.jarqprog.artapi.command.infrastructure.eventstore

import com.jarqprog.artapi.command.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.command.artdomain.EventStore
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.events.ResourceChanged
import com.jarqprog.artapi.command.artdomain.vo.Resource
import com.jarqprog.artapi.command.infrastructure.eventstore.inmemory.InMemoryEventStreamDatabase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors

internal class LoadingFutureEventsExperimental {

    private lateinit var eventStreamDatabase: EventStreamDatabase
    private lateinit var eventStore: EventStore

    @BeforeEach
    fun prepareStorage() {
        eventStreamDatabase = InMemoryEventStreamDatabase(ConcurrentHashMap())
        eventStore = EventStorage(eventStreamDatabase)
        mergeAndSortTwoHistories(HISTORY_WITH_THREE_EVENTS, ANOTHER_HISTORY)
                .forEach { event -> eventStore.save(event) }
    }

    // at the moment there's no possibility on the command level
    @Test
    fun shouldLoadAllEventsFromGivenPointInTheFuture() {

        val identifier = ANOTHER_HISTORY.first().artId()
        val lastVersion = ANOTHER_HISTORY.last().version()
        var currentVersion = lastVersion
        val hundredDaysInTheFuture = Instant.now().plus(100, ChronoUnit.DAYS)
        val ninetyDaysInTheFuture = hundredDaysInTheFuture.minus(10, ChronoUnit.DAYS)
        val eightyDaysInTheFuture = hundredDaysInTheFuture.minus(20, ChronoUnit.DAYS)

        val future = listOf<ArtEvent>(
                    ResourceChanged(
                            identifier,
                            ++currentVersion,
                            eightyDaysInTheFuture,
                            Resource("some_changed_path")
                    ),
                    ResourceChanged(
                            identifier,
                            ++currentVersion,
                            ninetyDaysInTheFuture,
                            Resource("some_changed_path_again")
                    ),
                    ResourceChanged(
                            identifier,
                            ++currentVersion,
                            hundredDaysInTheFuture,
                            Resource("some_changed_path_again_and_again")
                    )
        )

        val merged = mergeAndSortTwoHistories(ANOTHER_HISTORY, future)
        merged.forEach { event -> eventStore.save(event) }

        val firstFetchedHistory = eventStore.load(identifier, eightyDaysInTheFuture).get()
        val secondFetchedHistory = eventStore.load(identifier, ninetyDaysInTheFuture).get()
        val thirdFetchedHistory = eventStore.load(identifier, hundredDaysInTheFuture).get()

        val shouldBeTheSameAsSecond = eventStore.load(identifier, ninetyDaysInTheFuture
                .plusSeconds(120)).get()

        val firstExpectedHistory = filterHistoryByPointInTime(merged, eightyDaysInTheFuture)
        val secondExpectedHistory = filterHistoryByPointInTime(merged, ninetyDaysInTheFuture)
        val thirdExpectedHistory = filterHistoryByPointInTime(merged, hundredDaysInTheFuture)

        assertHistoryShouldMatchWithEvents(firstFetchedHistory, firstExpectedHistory)
        assertHistoryShouldMatchWithEvents(secondFetchedHistory, secondExpectedHistory)
        assertHistoryShouldMatchWithEvents(thirdFetchedHistory, thirdExpectedHistory)
        assertHistoryShouldMatchWithEvents(shouldBeTheSameAsSecond, secondFetchedHistory.events)
    }

    private fun filterHistoryByPointInTime(history: List<ArtEvent>, pointInTime: Instant): List<ArtEvent> {
        return history.stream()
                .filter { event -> event.timestamp() <= pointInTime }
                .collect(Collectors.toList())
    }
}