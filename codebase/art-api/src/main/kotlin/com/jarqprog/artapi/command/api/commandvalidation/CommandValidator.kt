package com.jarqprog.artapi.command.api.commandvalidation

import arrow.core.Either

import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.command.api.CommandValidation
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.command.api.commands.ArtCommand
import com.jarqprog.artapi.command.api.commands.ChangeResource
import com.jarqprog.artapi.command.api.commands.CreateArt
import com.jarqprog.artapi.command.api.exceptions.CommandProcessingFailure
import kotlinx.coroutines.runBlocking

class CommandValidator : CommandValidation {

    override fun validate(command: ArtCommand, history: ArtHistory, currentState: ArtAggregate):
            Either<CommandProcessingFailure, ArtCommand> {
        return when (command) {
            is CreateArt -> process(command, history, currentState)
            is ChangeResource -> process(command, history, currentState)
            else -> Either.left(CommandProcessingFailure("invalid unhandled command: $command"))
        }
    }

    private fun process(command: CreateArt, history: ArtHistory, currentState: ArtAggregate):
            Either<CommandProcessingFailure, ArtCommand> {

        return runBlocking {
            Either.catch {
                raiseFailureIf(command.version() != 0, "Incorrect version for $command. " +
                        "Expected version: 0")
                validateHistory(command, history)
                validateArtIdEquality(command, currentState)
                command
            }
                    .mapLeft { failure -> failure as CommandProcessingFailure }
        }
    }

    private fun process(command: ChangeResource, history: ArtHistory, currentState: ArtAggregate):
            Either<CommandProcessingFailure, ArtCommand> {
        return runBlocking {
            Either.catch {
                validateHistory(command, history)
                validateArtIdEquality(command, currentState)
                validateCommandVersionOnUpdate(command, currentState)
                raiseFailureIf(command.resource() != currentState.resource(),
                        "resource url for $command is the same - event not created")
                command
            }
                    .mapLeft { failure -> failure as CommandProcessingFailure }
        }
    }
}

private fun validateArtIdEquality(command: ArtCommand, currentState: ArtAggregate) {
    val commandArtUuid = command.artId()
    val artUuid = currentState.identifier()
    raiseFailureIf(commandArtUuid != artUuid,
            "invalid art identifier for $command, expected id: $artUuid")
}

private fun validateCommandVersionOnUpdate(command: ArtCommand, currentState: ArtAggregate) {
    val commandVersion = command.version()
    val expectedVersion = currentState.version().plus(1)

    raiseFailureIf(commandVersion != expectedVersion,
            "invalid version for $command, expected version: $expectedVersion")
}

private fun validateHistory(command: ArtCommand, history: ArtHistory) {
    when (command) {
        is CreateArt -> raiseFailureIf(history.isNotEmpty(),
                "error on processing $command - history is not empty")

        else -> raiseFailureIf(history.isEmpty(),
                "error on processing $command - history is empty")
    }
}

private fun raiseFailureIf(isTrue: Boolean, withMessage: String) {
    if (isTrue) throw CommandProcessingFailure(withMessage)
}