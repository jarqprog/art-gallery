package com.jarqprog.artapi.ports.outgoing.eventstore

import com.jarqprog.artapi.ports.outgoing.eventstore.entity.EventDescriptor
import com.jarqprog.artapi.domain.vo.Identifier
import reactor.core.publisher.Mono

interface EventStreamDatabase {

    fun historyExistsById(artId: Identifier): Mono<Boolean>
    fun streamVersion(artId: Identifier): Mono<Int>
    fun save(event: EventDescriptor): Mono<Void>
    fun load(artId: Identifier): Mono<List<EventDescriptor>>

}