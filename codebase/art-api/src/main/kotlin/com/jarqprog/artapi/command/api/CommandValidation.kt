package com.jarqprog.artapi.command.api

import com.jarqprog.artapi.domain.commands.ArtCommand
import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.domain.ArtHistory
import reactor.core.publisher.Mono

interface CommandValidation {

    fun validate(command: ArtCommand, history: ArtHistory, currentState: ArtAggregate):
            Mono<ArtCommand>

}