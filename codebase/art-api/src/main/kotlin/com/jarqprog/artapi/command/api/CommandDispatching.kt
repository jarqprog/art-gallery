package com.jarqprog.artapi.command.api

import arrow.core.Either
import com.jarqprog.artapi.command.api.commands.ArtCommand
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.ArtHistory

interface CommandDispatching {

    fun dispatch(command: ArtCommand, history: ArtHistory): Either<Throwable, ArtEvent>

}