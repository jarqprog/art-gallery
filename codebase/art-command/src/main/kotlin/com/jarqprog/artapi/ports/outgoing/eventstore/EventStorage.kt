package com.jarqprog.artapi.ports.outgoing.eventstore

import com.jarqprog.artapi.applicationservice.ProcessingResult
import com.jarqprog.artapi.domain.art.ArtHistory
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.ports.outgoing.eventstore.exceptions.EventStoreFailure

import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import java.time.Instant

class EventStorage(
        private val eventStreamRepository: EventStreamRepository,
        private val snapshotRepository: SnapshotRepository
): EventStore {

    private val snapshotPersistingFrequency = 2//todo should be parametrized
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun save(processingResult: ProcessingResult): Mono<Void> {
        logger.debug("about to save ${processingResult.artEvent}")
        return when (processingResult.artEvent) {
            is ArtCreated -> initializeStream(processingResult.artEvent)
            else -> appendToStream(processingResult)
        }
    }

    override fun load(artId: Identifier): Mono<ArtHistory> {
        return eventStreamRepository.load(artId)
                .doOnNext { logger.debug("fetching history for art id: $artId") }
                .filter { events -> events.isNotEmpty() }
                .doOnNext { logger.debug("fetched history for art id: $artId") }
                //fetch optional snapshot
                .map { eventDescriptors -> ArtHistory.with(artId, eventDescriptors) }
    }

    override fun load(artId: Identifier, stateAt: Instant): Mono<ArtHistory> {
        return eventStreamRepository.load(artId)
                .doOnNext { logger.debug("fetching history for art id: $artId") }
                .filter { events -> events.isNotEmpty() }
                .doOnNext { logger.debug("fetched history for art id: $artId") }
                .map { historyDescriptor -> ArtHistory.from(artId, historyDescriptor, stateAt) }
    }

    private fun initializeStream(event: ArtCreated): Mono<Void> {
        return validateVersionOnInitializingHistory(event)
                .flatMap(this::preventOverridingStream)
                .map(ArtEvent::asDescriptor)
                .flatMap(eventStreamRepository::save)
    }

    private fun appendToStream(processingResult: ProcessingResult): Mono<Void> {
        return validateVersionOnAppendingEvent(processingResult.artEvent)
                .map(ArtEvent::asDescriptor)
                .flatMap { descriptor -> eventStreamRepository.save(descriptor) }
                .thenEmpty(saveSnapshot(processingResult))
    }

    private fun preventOverridingStream(event: ArtEvent): Mono<ArtEvent> {
        val artId = event.artId()
        return eventStreamRepository.historyExistsById(artId)
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
        return eventStreamRepository.streamVersion(artId)
                .switchIfEmpty(Mono.error(EventStoreFailure.notFoundBy(artId)))
                .filter { streamVersion -> streamVersion == event.version().minus(1) }
                .map { event }
                .switchIfEmpty(Mono.error(EventStoreFailure("Incorrect version for $event")))
    }

    private fun saveSnapshot(processingResult: ProcessingResult): Mono<Void> {
        logger.info("about to save snapshot for art id: ${processingResult.artIdentifier()}")
        return snapshotRepository.save(processingResult.currentState.asDescriptor())
    }
}