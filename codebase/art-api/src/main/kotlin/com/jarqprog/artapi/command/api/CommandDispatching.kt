package com.jarqprog.artapi.command.api

import com.jarqprog.artapi.domain.commands.ArtCommand
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.ArtHistory
import reactor.core.publisher.Mono

interface CommandDispatching {

    fun dispatch(command: ArtCommand, history: ArtHistory): Mono<ArtEvent>

}