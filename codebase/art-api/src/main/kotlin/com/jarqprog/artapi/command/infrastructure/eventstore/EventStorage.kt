package com.jarqprog.artapi.command.infrastructure.eventstore

import com.jarqprog.artapi.command.artdomain.ArtHistory
import com.jarqprog.artapi.command.artdomain.EventStore
import com.jarqprog.artapi.command.artdomain.events.ArtCreated
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.ArtHistoryDescriptor
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.EventToDescriptor
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.FilteredHistoryTransformation
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.HistoryTransformation
import com.jarqprog.artapi.command.infrastructure.eventstore.exceptions.EventStoreFailure
import io.vavr.control.Try
import java.time.Instant
import java.util.*

class EventStorage(private val eventStreamDatabase: EventStreamDatabase) : EventStore {

    private val eventToDescriptor = EventToDescriptor()
    private val historyTransformation = HistoryTransformation()
    private val filteredHistoryTransformation = FilteredHistoryTransformation()

    override fun save(event: ArtEvent): Optional<EventStoreFailure> {
        return when (event) {
            is ArtCreated -> initializeStream(event)
            else -> appendToStream(event)
        }
    }

    override fun load(artId: Identifier): Optional<ArtHistory> {
        return eventStreamDatabase.load(artId)
                .map(historyTransformation)
    }

    override fun load(artId: Identifier, stateAt: Instant): Optional<ArtHistory> {
        return eventStreamDatabase.load(artId)
                .map { historyDescriptor -> filteredHistoryTransformation.apply(historyDescriptor, stateAt) }
    }

    private fun initializeStream(event: ArtCreated): Optional<EventStoreFailure> {
        val artIdentifier = event.artId()
        val version = event.version()
        val timestamp = event.timestamp()
        return Try.run {
            if (version != 0) throw EventStoreFailure("Incorrect version. Expected: 0 but is: $version")
            if (eventStreamDatabase.historyExistsById(artIdentifier)) throw EventStoreFailure("History already exists for art id=$artIdentifier")
            val newEventStream = ArtHistoryDescriptor(
                    artIdentifier.value,
                    version,
                    timestamp,
                    listOf(eventToDescriptor.apply(event))
            )
            eventStreamDatabase.save(newEventStream)
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
                    .orElseThrow { EventStoreFailure.notFound(event.artId()) }
        }
                .onFailure { ex -> EventStoreFailure(ex.localizedMessage, ex) }
                .fold(
                        { ex -> Optional.of(EventStoreFailure(ex.localizedMessage, ex)) },
                        { Optional.empty() }
                )
    }

    private fun preventConcurrentWrite(event: ArtEvent) {
        eventStreamDatabase.streamVersion(event)
                .ifPresent { streamVersion ->
                    if (isNotExpectedVersion(streamVersion, event))
                        throw EventStoreFailure.concurrentWrite(event)
                }
    }

    private fun isNotExpectedVersion(streamVersion: Int, event: ArtEvent) = streamVersion != event.version().minus(1)

}