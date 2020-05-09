package com.jarqprog.artapi.command.artdomain.commanddispatching

import arrow.core.Either
import com.jarqprog.artapi.command.artdomain.Art
import com.jarqprog.artapi.command.artdomain.CommandValidation
import com.jarqprog.artapi.command.artdomain.commands.ArtCommand
import com.jarqprog.artapi.command.artdomain.commands.ChangeResource
import com.jarqprog.artapi.command.artdomain.commands.CreateArt
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.exceptions.CommandProcessingFailure
import io.vavr.control.Try

class CommandValidator : CommandValidation {

    override fun validate(command: ArtCommand, history: List<ArtEvent>, currentState: Art):
            Either<CommandProcessingFailure, ArtCommand> {
        return when (command) {
            is CreateArt -> process(command, history, currentState)
            is ChangeResource -> process(command, history, currentState)
            else -> Either.left(CommandProcessingFailure("invalid unhandled command: $command"))
        }
    }

    private fun process(command: CreateArt, history: List<ArtEvent>, currentState: Art):
            Either<CommandProcessingFailure, ArtCommand> {

        return Try.ofCallable {
            validateHistory(command, history)
            validateArtUuidEquality(command, currentState)
            validateCommandVersionOnUpdate(command, currentState)
            Either.right(command)
        }
                .onFailure { ex -> Either.left(ex) }
                .get()
    }

    private fun process(command: ChangeResource, events: List<ArtEvent>, currentState: Art):
            Either<CommandProcessingFailure, ArtCommand> {

        return Try.ofCallable {
            validateHistory(command, events)
            validateArtUuidEquality(command, currentState)
            validateCommandVersionOnUpdate(command, currentState)
            raiseFailureIf(command.resource() != currentState.resource(),
                    "resource url is the same - event not created")
            Either.right(command)
        }
                .onFailure { ex -> Either.left(ex) }
                .get()
    }

    private fun validateArtUuidEquality(command: ArtCommand, currentState: Art) {
        val commandArtUuid = command.artId()
        val artUuid = currentState.identifier()
        raiseFailureIf(commandArtUuid != artUuid,
                "invalid uuid, expected $artUuid but was: $commandArtUuid")
    }

    private fun validateCommandVersionOnUpdate(command: ArtCommand, currentState: Art) {
        val commandVersion = command.version()
        val expectedVersion = currentState.version().plus(1)
        raiseFailureIf(commandVersion != expectedVersion,
                "invalid version, expected $expectedVersion but was: $commandVersion")
    }

    private fun validateHistory(command: ArtCommand, history: List<ArtEvent>) {
        when (command) {
            is CreateArt -> raiseFailureIf(history.isNotEmpty(),
                    "error on processing $command - history is not empty: $history")

            else -> raiseFailureIf(history.isEmpty(),
                    "error on processing $command - history is  empty")
        }
    }

    private fun raiseFailureIf(isTrue: Boolean, withMessage: String) {
        if (isTrue) throw CommandProcessingFailure(withMessage)
    }
}