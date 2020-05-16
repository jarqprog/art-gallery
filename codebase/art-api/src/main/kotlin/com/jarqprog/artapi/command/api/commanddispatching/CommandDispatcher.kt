package com.jarqprog.artapi.command.api.commanddispatching

import arrow.core.Either
import com.jarqprog.artapi.domain.Art
import com.jarqprog.artapi.command.api.CommandDispatching
import com.jarqprog.artapi.command.api.CommandValidation
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.command.api.commands.ArtCommand
import com.jarqprog.artapi.command.api.commands.ChangeResource
import com.jarqprog.artapi.command.api.commands.CreateArt
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.events.ResourceChanged
import com.jarqprog.artapi.command.api.exceptions.CommandProcessingFailure
import java.time.Instant

class CommandDispatcher(private val validation: CommandValidation) : CommandDispatching {

    override fun dispatch(command: ArtCommand, history: ArtHistory): Either<Throwable, ArtEvent> {

        return when (command) {
            is CreateArt -> process(command, history)
            is ChangeResource -> process(command, history)
            else -> throw CommandProcessingFailure("invalid unhandled command: $command")
        }
    }

    private fun process(command: CreateArt, history: ArtHistory): Either<Throwable, ArtEvent> {

        val initialState = Art.initialState(command.artId())
        return validation.validate(command, history, initialState)
                .map {
                    ArtCreated(
                            command.artId(),
                            command.version(),
                            Instant.now(),
                            command.author(),
                            command.resource(),
                            command.addedBy(),
                            command.artGenre(),
                            command.artStatus()
                    )
                }
    }

    private fun process(command: ChangeResource, history: ArtHistory): Either<Throwable, ArtEvent> {

        val uuid = command.artId()
        val currentState = Art.replayAll(uuid, history)
        return validation.validate(command, history, currentState)
                .map {
                    ResourceChanged(
                            uuid,
                            command.version(),
                            Instant.now(),
                            command.resource()
                    )
                }
    }
}