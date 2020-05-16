package com.jarqprog.artapi.command.api

import arrow.core.Either
import com.jarqprog.artapi.command.api.commands.ArtCommand
import com.jarqprog.artapi.command.api.exceptions.CommandProcessingFailure
import com.jarqprog.artapi.domain.Art
import com.jarqprog.artapi.domain.ArtHistory

interface CommandValidation {

    fun validate(command: ArtCommand, history: ArtHistory, currentState: Art):
            Either<CommandProcessingFailure, ArtCommand>

}