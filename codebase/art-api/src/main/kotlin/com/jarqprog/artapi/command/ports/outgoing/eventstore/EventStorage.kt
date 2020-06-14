package com.jarqprog.artapi.command.ports.outgoing.eventstore

import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ToDescriptor
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.FilteredHistoryTransformation
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.HistoryTransformation
import com.jarqprog.artapi.command.ports.outgoing.eventstore.exceptions.EventStoreFailure

import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import java.time.Instant

class EventStorage(
        private val eventStreamDatabase: EventStreamDatabase,
        private val snapshotDatabase: SnapshotDatabase
) : EventStore {

    private val eventToDescriptor = ToDescriptor()
    private val historyTransformation = HistoryTransformation()
    private val filteredHistoryTransformation = FilteredHistoryTransformation()

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun save(event: ArtEvent): Mono<Void> {
        logger.debug("about to save $event")
        return when (event) {
            is ArtCreated -> initializeStream(event)
            else -> appendToStream(event)
        }
    }

    override fun load(artId: Identifier): Mono<ArtHistory> {
        return eventStreamDatabase.load(artId)
                .doOnNext { logger.debug("fetching history for art id: $artId") }
                .filter { events -> events.isNotEmpty() }
                .doOnNext { logger.debug("fetched history for art id: $artId") }
                .map { historyDescriptor ->
                    historyTransformation.asHistory(historyDescriptor,
                            snapshotDatabase.loadLatest(artId))
                }
    }

    override fun load(artId: Identifier, stateAt: Instant): Mono<ArtHistory> {
        return eventStreamDatabase.load(artId)
                .doOnNext { logger.debug("fetching history for art id: $artId") }
                .filter { events -> events.isNotEmpty() }
                .doOnNext { logger.debug("fetched history for art id: $artId") }
                .map { historyDescriptor -> filteredHistoryTransformation.asHistory(artId, historyDescriptor, stateAt) }
    }

    private fun initializeStream(event: ArtCreated): Mono<Void> {
        return validateVersionOnInitializingHistory(event)
                .flatMap(this::preventOverridingStream)
                .map(eventToDescriptor::apply)
                .flatMap(eventStreamDatabase::save)
    }

    private fun appendToStream(event: ArtEvent): Mono<Void> {
        return validateVersionOnAppendingEvent(event)
                .map(eventToDescriptor::apply)
                .flatMap(eventStreamDatabase::save)
    }

    private fun preventOverridingStream(event: ArtEvent): Mono<ArtEvent> {
        val artId = event.artId()
        return eventStreamDatabase.historyExistsById(artId)
                .filter { result -> result != true }
                .switchIfEmpty(Mono.error(EventStoreFailure("History already exists for art id=$artId")))
                .map { event }
    }

    private fun validateVersionOnInitializingHistory(event: ArtCreated): Mono<ArtEvent> {
        return Mono.just<ArtEvent>(event)
                .filter { artCreated -> artCreated.version() == 0 }
                .switchIfEmpty(Mono.error(EventStoreFailure("Incorrect version for $event")))
    }

    private fun validateVersionOnAppendingEvent(event: ArtEvent): Mono<ArtEvent> {
        val artId = event.artId()
        return eventStreamDatabase.streamVersion(artId)
                .switchIfEmpty(Mono.error(EventStoreFailure.notFoundBy(artId)))
                .filter { streamVersion -> streamVersion == event.version().minus(1) }
                .map { event }
                .switchIfEmpty(Mono.error(EventStoreFailure("Incorrect version for $event")))
    }
}