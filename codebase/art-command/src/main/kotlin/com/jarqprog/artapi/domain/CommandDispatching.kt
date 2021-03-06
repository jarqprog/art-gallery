package com.jarqprog.artapi.domain

import com.jarqprog.artapi.applicationservice.commands.ArtCommand
import com.jarqprog.artapi.domain.art.ArtHistory
import com.jarqprog.artapi.domain.art.ProcessingResult
import reactor.core.publisher.Mono

interface CommandDispatching {
    fun dispatch(command: ArtCommand, history: ArtHistory): Mono<ProcessingResult>
}
