package com.jarqprog.artapi.ports.outgoing.eventstore

import com.jarqprog.artapi.domain.art.ArtDescriptor
import com.jarqprog.artapi.domain.vo.Identifier
import reactor.core.publisher.Mono

interface SnapshotRepository {
    fun save(art: ArtDescriptor): Mono<Void>
    fun loadLatest(artId: Identifier): Mono<ArtDescriptor>
}