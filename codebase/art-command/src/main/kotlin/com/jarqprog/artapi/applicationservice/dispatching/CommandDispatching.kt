package com.jarqprog.artapi.applicationservice.dispatching

import com.jarqprog.artapi.applicationservice.commands.ArtCommand
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.ArtHistory
import reactor.core.publisher.Mono

interface CommandDispatching {

    fun dispatch(command: ArtCommand, history: ArtHistory): Mono<ArtEvent>

}