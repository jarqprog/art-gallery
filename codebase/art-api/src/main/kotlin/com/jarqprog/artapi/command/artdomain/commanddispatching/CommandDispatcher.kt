package com.jarqprog.artapi.command.artdomain.commanddispatching

import arrow.core.Either
import com.jarqprog.artapi.command.artdomain.Art
import com.jarqprog.artapi.command.artdomain.CommandDispatching
import com.jarqprog.artapi.command.artdomain.CommandValidation
import com.jarqprog.artapi.command.artdomain.ArtHistory
import com.jarqprog.artapi.command.artdomain.commands.ArtCommand
import com.jarqprog.artapi.command.artdomain.commands.ChangeResource
import com.jarqprog.artapi.command.artdomain.commands.CreateArt
import com.jarqprog.artapi.command.artdomain.events.ArtCreated
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.events.ResourceChanged
import com.jarqprog.artapi.command.artdomain.exceptions.CommandProcessingFailure
import java.time.Instant

class CommandDispatcher(private val validation: CommandValidation) : CommandDispatching {

    override fun dispatch(command: ArtCommand, history: ArtHistory): Either<CommandProcessingFailure, ArtEvent> {

        return when (command) {
            is CreateArt -> process(command, history)
            is ChangeResource -> process(command, history)
            else -> throw CommandProcessingFailure("invalid unhandled command: $command")
        }
    }

    private fun process(command: CreateArt, history: ArtHistory): Either<CommandProcessingFailure, ArtEvent> {

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

    private fun process(command: ChangeResource, history: ArtHistory): Either<CommandProcessingFailure, ArtEvent> {

        val uuid = command.artId()
        val currentState = Art.replayAll(uuid, history.events)
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