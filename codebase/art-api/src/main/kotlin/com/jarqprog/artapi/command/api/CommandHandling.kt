package com.jarqprog.artapi.command.api

import com.jarqprog.artapi.domain.commands.ArtCommand
import reactor.core.publisher.Mono

interface CommandHandling {

    fun handle(command: ArtCommand): Mono<Void>

}