package com.jarqprog.artapi.command.ports.outgoing.eventstore

import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.vo.Identifier
import reactor.core.publisher.Mono
import java.time.Instant

interface EventStore {

    fun save(event: ArtEvent): Mono<Void>
    fun load(artId: Identifier): Mono<ArtHistory>
    fun load(artId: Identifier, stateAt: Instant): Mono<ArtHistory>

}