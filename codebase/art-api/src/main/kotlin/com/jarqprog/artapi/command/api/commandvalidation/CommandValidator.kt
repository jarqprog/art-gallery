package com.jarqprog.artapi.command.api.commandvalidation

import arrow.core.Either

import com.jarqprog.artapi.command.domain.Art
import com.jarqprog.artapi.command.api.CommandValidation
import com.jarqprog.artapi.command.domain.ArtHistory
import com.jarqprog.artapi.command.domain.commands.ArtCommand
import com.jarqprog.artapi.command.domain.commands.ChangeResource
import com.jarqprog.artapi.command.domain.commands.CreateArt
import com.jarqprog.artapi.command.api.exceptions.CommandProcessingFailure
import kotlinx.coroutines.runBlocking

class CommandValidator : CommandValidation {

    override fun validate(command: ArtCommand, history: ArtHistory, currentState: Art):
            Either<CommandProcessingFailure, ArtCommand> {
        return when (command) {
            is CreateArt -> process(command, history, currentState)
            is ChangeResource -> process(command, history, currentState)
            else -> Either.left(CommandProcessingFailure("invalid unhandled command: $command"))
        }
    }

    private fun process(command: CreateArt, history: ArtHistory, currentState: Art):
            Either<CommandProcessingFailure, ArtCommand> {

        return runBlocking {
            Either.catch {
                validateHistory(command, history)
                validateArtIdEquality(command, currentState)
                validateCommandVersionOnUpdate(command, currentState)
                command
            }
                    .mapLeft { failure -> failure as CommandProcessingFailure }
        }
    }

    private fun process(command: ChangeResource, history: ArtHistory, currentState: Art):
            Either<CommandProcessingFailure, ArtCommand> {
        return runBlocking {
            Either.catch {
                validateHistory(command, history)
                validateArtIdEquality(command, currentState)
                validateCommandVersionOnUpdate(command, currentState)
                raiseFailureIf(command.resource() != currentState.resource(),
                        "resource url is the same - event not created")
                command
            }
                    .mapLeft { failure -> failure as CommandProcessingFailure }
        }
    }
}

private fun validateArtIdEquality(command: ArtCommand, currentState: Art) {
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

private fun validateHistory(command: ArtCommand, history: ArtHistory) {
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