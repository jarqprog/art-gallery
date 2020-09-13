package com.jarqprog.artapi.applicationservice.handler

import com.jarqprog.artapi.applicationservice.handler.commands.ArtCommand
import reactor.core.publisher.Mono

interface CommandHandling {

    fun handle(command: ArtCommand): Mono<Void>

}