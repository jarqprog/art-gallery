package com.jarqprog.artapi.command.artdomain

import arrow.core.Either
import com.jarqprog.artapi.command.artdomain.commands.ArtCommand
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.exceptions.CommandProcessingFailure

interface CommandValidation {

    fun validate(command: ArtCommand, history: List<ArtEvent>, currentState: Art):
            Either<CommandProcessingFailure, ArtCommand>

}