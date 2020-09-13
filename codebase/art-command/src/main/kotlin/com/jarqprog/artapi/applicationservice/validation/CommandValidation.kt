package com.jarqprog.artapi.applicationservice.validation

import com.jarqprog.artapi.applicationservice.commands.ArtCommand
import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.domain.ArtHistory
import reactor.core.publisher.Mono

interface CommandValidation {

    fun validate(command: ArtCommand, history: ArtHistory, currentState: ArtAggregate):
            Mono<ArtCommand>

}