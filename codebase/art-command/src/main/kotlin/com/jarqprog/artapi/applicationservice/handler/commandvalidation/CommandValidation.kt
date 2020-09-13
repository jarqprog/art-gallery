package com.jarqprog.artapi.applicationservice.handler.commandvalidation

import com.jarqprog.artapi.applicationservice.handler.commands.ArtCommand
import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.domain.ArtHistory
import reactor.core.publisher.Mono

interface CommandValidation {

    fun validate(command: ArtCommand, history: ArtHistory, currentState: ArtAggregate):
            Mono<ArtCommand>

}