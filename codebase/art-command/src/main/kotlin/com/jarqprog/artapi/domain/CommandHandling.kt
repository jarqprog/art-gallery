package com.jarqprog.artapi.domain

import com.jarqprog.artapi.applicationservice.commands.ArtCommand
import reactor.core.publisher.Mono

interface CommandHandling {
    fun handle(command: ArtCommand): Mono<Void>
}