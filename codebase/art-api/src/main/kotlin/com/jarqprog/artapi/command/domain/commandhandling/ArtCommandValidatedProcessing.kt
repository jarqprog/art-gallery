package com.jarqprog.artapi.command.domain.commandhandling

import arrow.core.Either
import com.jarqprog.artapi.command.domain.Art
import com.jarqprog.artapi.command.domain.ArtCommandHandling
import com.jarqprog.artapi.command.domain.ArtCommandValidation
import com.jarqprog.artapi.command.domain.commands.ArtCommand
import com.jarqprog.artapi.command.domain.commands.ChangeResource
import com.jarqprog.artapi.command.domain.commands.CreateArt
import com.jarqprog.artapi.command.domain.events.ArtCreated
import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.domain.events.ResourceChanged
import com.jarqprog.artapi.command.domain.exceptions.CommandProcessingFailure
import java.time.Instant

class ArtCommandValidatedProcessing(private val validation: ArtCommandValidation): ArtCommandHandling {

    override fun handle(command: ArtCommand, history: List<ArtEvent>): Either<CommandProcessingFailure,ArtEvent> {

        return when (command) {
            is CreateArt -> process(command, history)
            is ChangeResource -> process(command, history)
            else -> throw IllegalArgumentException("invalid unhandled command: $command")
        }
    }

    private fun process(command: CreateArt, history: List<ArtEvent>): Either<CommandProcessingFailure,ArtEvent> {

        val initialState = Art.initialState(command.artUuid())
        return validation.validate(command, history, initialState)
                .map {
                    ArtCreated(
                            command.artUuid(),
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

    private fun process(command: ChangeResource, history: List<ArtEvent>): Either<CommandProcessingFailure,ArtEvent> {

        val uuid = command.artUuid()
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