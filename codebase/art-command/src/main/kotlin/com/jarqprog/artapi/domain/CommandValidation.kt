package com.jarqprog.artapi.domain

import com.jarqprog.artapi.applicationservice.commands.ArtCommand
import com.jarqprog.artapi.domain.art.ArtAggregate
import com.jarqprog.artapi.domain.art.ArtHistory
import reactor.core.publisher.Mono

interface CommandValidation {
    fun validate(command: ArtCommand, history: ArtHistory, currentState: ArtAggregate): Mono<ArtCommand>
}