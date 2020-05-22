package com.jarqprog.artapi.command.ports.outgoing.eventstore

import arrow.core.Either
import com.jarqprog.artapi.command.api.exceptions.IncorrectVersion
import com.jarqprog.artapi.command.api.exceptions.NotFound
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ArtHistoryDescriptor
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ToDescriptor
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.FilteredHistoryTransformation
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.HistoryTransformation
import com.jarqprog.artapi.command.ports.outgoing.eventstore.exceptions.EventStoreFailure
import io.vavr.control.Try

import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.*

class EventStorage(private val eventStreamDatabase: EventStreamDatabase) : EventStore {

    private val eventToDescriptor = ToDescriptor()
    private val historyTransformation = HistoryTransformation()
    private val filteredHistoryTransformation = FilteredHistoryTransformation()

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun save(event: ArtEvent): Optional<EventStoreFailure> {
        logger.info("about to save event $event")
        return when (event) {
            is ArtCreated -> initializeStream(event)
            else -> appendToStream(event)
        }
    }

    override fun load(artId: Identifier): Either<EventStoreFailure, Optional<ArtHistory>> {
        return runBlocking {
            Either.catch {
                eventStreamDatabase.load(artId)
                        .map(historyTransformation)
                        .also { logger.info("fetching history for art id: $artId") }
            }
                    .mapLeft { failure -> EventStoreFailure.fromException(failure) }
        }
    }

    override fun load(artId: Identifier, stateAt: Instant): Either<EventStoreFailure, Optional<ArtHistory>> {
        return runBlocking {
            Either.catch {
                eventStreamDatabase.load(artId)
                        .map { historyDescriptor -> filteredHistoryTransformation.apply(historyDescriptor, stateAt) }
                        .also { logger.info("fetching history for art id: $artId") }
            }
                    .mapLeft { failure -> EventStoreFailure.fromException(failure) }
        }
    }

    private fun initializeStream(event: ArtCreated): Optional<EventStoreFailure> {
        val artIdentifier = event.artId()
        val version = event.version()
        val timestamp = event.timestamp()
        return Try.run {
            if (eventStreamDatabase.historyExistsById(artIdentifier)) throw EventStoreFailure("History already exists for art id=$artIdentifier")
            val newEventStream = ArtHistoryDescriptor(
                    artIdentifier.value,
                    version,
                    timestamp,
                    listOf(eventToDescriptor.apply(event))
            )
            eventStreamDatabase.save(newEventStream)
            logger.info("initialized event stream with event: $event")
        }
                .fold(
                        { ex -> Optional.of(EventStoreFailure(ex.localizedMessage, ex)) },
                        { Optional.empty() }
                )
    }

    private fun appendToStream(event: ArtEvent): Optional<EventStoreFailure> {
        return Try.run {
            preventConcurrentWrite(event)
            eventStreamDatabase.load(event.artId())
                    .map { eventStream -> eventStream.add(eventToDescriptor.apply(event)) }
                    .map { updated -> eventStreamDatabase.save(updated) }
                    .orElseThrow { NotFound(event.artId()) }
            logger.info("event $event appended to stream")
        }
                .onFailure { ex -> EventStoreFailure(ex.localizedMessage, ex) }
                .fold(
                        { ex -> Optional.of(EventStoreFailure(ex.localizedMessage, ex)) },
                        { Optional.empty() }
                )
    }

    private fun preventConcurrentWrite(event: ArtEvent) {
        eventStreamDatabase.streamVersion(event.artId())
                .ifPresent { streamVersion ->
                    if (isNotExpectedVersion(streamVersion, event))
                        throw IncorrectVersion.fromEvent(event)
                }
    }

    private fun isNotExpectedVersion(streamVersion: Int, event: ArtEvent) = streamVersion != event.version().minus(1)

}