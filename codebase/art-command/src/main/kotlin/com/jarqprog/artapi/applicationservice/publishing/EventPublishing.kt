package com.jarqprog.artapi.applicationservice.publishing

import com.jarqprog.artapi.domain.events.ArtEvent
import reactor.core.publisher.Mono

interface EventPublishing {

    fun publish(event: ArtEvent): Mono<Void>

}