package com.jarqprog.artapi.applicationservice.handler.eventpublishing

import com.jarqprog.artapi.domain.events.ArtEvent
import reactor.core.publisher.Mono

interface EventPublishing {

    fun publish(event: ArtEvent): Mono<Void>

}