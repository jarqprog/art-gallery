package com.jarqprog.artapi.command.api

import arrow.core.Either
import com.jarqprog.artapi.command.domain.commands.ArtCommand
import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.api.exceptions.CommandProcessingFailure
import com.jarqprog.artapi.command.domain.ArtHistory

interface CommandDispatching {

    fun dispatch(command: ArtCommand, history: ArtHistory): Either<Throwable, ArtEvent>

}