package com.jarqprog.artapi.command.domain

import arrow.core.Either
import com.jarqprog.artapi.command.domain.Art
import com.jarqprog.artapi.command.domain.commands.ArtCommand
import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.domain.exceptions.CommandProcessingFailure

interface ArtCommandValidation {

    fun validate(command: ArtCommand, history: List<ArtEvent>, currentState: Art):
            Either<CommandProcessingFailure,ArtCommand>

}