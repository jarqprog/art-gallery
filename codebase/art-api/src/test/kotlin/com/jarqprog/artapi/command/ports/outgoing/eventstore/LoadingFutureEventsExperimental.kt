package com.jarqprog.artapi.command.ports.outgoing.eventstore

import arrow.core.getOrHandle
import com.jarqprog.artapi.command.HISTORY_WITH_THREE_EVENTS
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.events.ResourceChanged
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.inmemory.InMemoryEventStreamDatabase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap

internal class LoadingFutureEventsExperimental {

    private lateinit var eventStreamDatabase: EventStreamDatabase
    private lateinit var eventStore: EventStore

    @BeforeEach
    fun prepareStorage() {
        eventStreamDatabase = InMemoryEventStreamDatabase(ConcurrentHashMap())
        eventStore = EventStorage(eventStreamDatabase)
        HISTORY_WITH_THREE_EVENTS.events().plus(ANOTHER_HISTORY.events())
                .forEach { event -> eventStore.save(event) }
    }

    // at the moment there's no possibility on the command level
    @Test
    fun shouldLoadAllEventsFromGivenPointInTheFuture() {

        val identifier = ANOTHER_HISTORY.artId()
        val lastVersion = ANOTHER_HISTORY.version()
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

        future.forEach { event -> eventStore.save(event) }
        val merged = ANOTHER_HISTORY.events().plus(future)

        val firstExpectedHistory = ArtHistory(identifier, filterEventsByPointInTime(merged, eightyDaysInTheFuture))
        eventStore.load(identifier, eightyDaysInTheFuture)
                .map { optionalHistory ->
                    optionalHistory
                            .map { history -> assertHistoriesAreTheSame(history, firstExpectedHistory) }
                            .orElseGet { Assertions.fail("was empty") }
                }
                .getOrHandle { Assertions.fail("has failure") }

        val secondExpectedHistory = ArtHistory(identifier, filterEventsByPointInTime(merged, ninetyDaysInTheFuture))
        eventStore.load(identifier, ninetyDaysInTheFuture)
                .map { optionalHistory ->
                    optionalHistory
                            .map { history -> assertHistoriesAreTheSame(history, secondExpectedHistory) }
                            .orElseGet { Assertions.fail("was empty") }
                }
                .getOrHandle { Assertions.fail("has failure") }


        val thirdExpectedHistory = ArtHistory(identifier, filterEventsByPointInTime(merged, hundredDaysInTheFuture))
        eventStore.load(identifier, hundredDaysInTheFuture)
                .map { optionalHistory ->
                    optionalHistory
                            .map { history -> assertHistoriesAreTheSame(history, thirdExpectedHistory) }
                            .orElseGet { Assertions.fail("was empty") }
                }
                .getOrHandle { Assertions.fail("has failure") }
    }
}