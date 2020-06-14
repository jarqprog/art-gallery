package com.jarqprog.artapi.command.api

import com.jarqprog.artapi.domain.events.ArtEvent
import reactor.core.publisher.Mono

interface EventPublishing {

    fun publish(event: ArtEvent): Mono<Void>

}