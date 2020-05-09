package com.jarqprog.artapi.command.infrastructure.eventstore

import com.jarqprog.artapi.command.artdomain.EventStore
import com.jarqprog.artapi.command.artdomain.events.ArtCreated
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.EventDescriptor
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.EventStream
import com.jarqprog.artapi.command.infrastructure.eventstore.exceptions.EventStoreFailure
import io.vavr.control.Try
import io.vavr.kotlin.stream
import java.time.Instant
import java.util.*
import java.util.stream.Collectors

class EventStorage(private val database: Database) : EventStore {

    private val eventToDescriptor = EventToDescriptor()
    private val descriptorToEvent = DescriptorToEvent()

    override fun save(event: ArtEvent): Optional<EventStoreFailure> {
        return when (event) {
            is ArtCreated -> initializeStream(event)
            else -> appendToStream(event)
        }
    }

    override fun load(artId: Identifier) = load(artId, Instant.now())

    override fun load(artId: Identifier, stateAt: Instant): Optional<List<ArtEvent>> {
        return database.load(artId)
                .map {
                    stream<EventDescriptor>()
                            .filter { eventDescriptor -> eventDescriptor.timestamp <= stateAt }
                            .map(descriptorToEvent::apply)
                            .collect(Collectors.toList())
                }
    }

    private fun initializeStream(event: ArtCreated): Optional<EventStoreFailure> {
        val artIdentifier = event.artId()
        val version = event.version()
        return Try.run {
            if (version != 0) throw EventStoreFailure("Incorrect version. Expected: 0 but is: $version")
            if (database.existsById(artIdentifier)) throw EventStoreFailure("History already exists for art id=$artIdentifier")
            val newEventStream = EventStream(
                    artIdentifier.value,
                    version,
                    listOf(eventToDescriptor.apply(event))
            )
            database.save(newEventStream)
        }
                .fold(
                        { ex -> Optional.of(EventStoreFailure(ex.localizedMessage, ex)) },
                        { Optional.empty() }
                )
    }

    private fun appendToStream(event: ArtEvent): Optional<EventStoreFailure> {
        return Try.run {
            preventConcurrentWrite(event)
            database.load(event.artId())
                    .map { eventStream -> eventStream.add(eventToDescriptor.apply(event)) }
                    .map { updated -> database.save(updated) }
                    .orElseThrow { EventStoreFailure.notFound(event.artId()) }
        }
                .onFailure { ex -> EventStoreFailure(ex.localizedMessage, ex) }
                .fold(
                        { ex -> Optional.of(EventStoreFailure(ex.localizedMessage, ex)) },
                        { Optional.empty() }
                )
    }

    private fun preventConcurrentWrite(event: ArtEvent) {
        database.streamVersion(event)
                .ifPresent { streamVersion ->
                    if (isNotExpectedVersion(streamVersion, event))
                        throw EventStoreFailure.concurrentWrite(event)
                }
    }

    private fun isNotExpectedVersion(streamVersion: Int, event: ArtEvent) = streamVersion != event.version().minus(1)


}