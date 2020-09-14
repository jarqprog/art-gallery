package com.jarqprog.artapi.applicationservice

import com.jarqprog.artapi.applicationservice.commands.ArtCommand
import reactor.core.publisher.Mono

interface CommandHandling {

    fun handle(command: ArtCommand): Mono<Void>

}