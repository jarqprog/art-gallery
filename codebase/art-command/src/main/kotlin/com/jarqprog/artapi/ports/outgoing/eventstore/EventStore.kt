package com.jarqprog.artapi.ports.outgoing.eventstore

import com.jarqprog.artapi.applicationservice.ProcessingResult
import com.jarqprog.artapi.domain.art.ArtHistory
import com.jarqprog.artapi.domain.vo.Identifier
import reactor.core.publisher.Mono
import java.time.Instant

interface EventStore {
    fun save(processingResult: ProcessingResult): Mono<Void>
    fun load(artId: Identifier): Mono<ArtHistory>
    fun load(artId: Identifier, stateAt: Instant): Mono<ArtHistory>
}