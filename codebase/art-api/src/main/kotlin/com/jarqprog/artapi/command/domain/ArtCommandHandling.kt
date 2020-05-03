package com.jarqprog.artapi.command.domain

import arrow.core.Either
import com.jarqprog.artapi.command.domain.commands.ArtCommand
import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.domain.exceptions.CommandProcessingFailure

interface ArtCommandHandling {

    fun handle(command: ArtCommand, history: List<ArtEvent>): Either<CommandProcessingFailure,ArtEvent>

}