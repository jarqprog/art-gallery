package com.jarqprog.artapi.applicationservice.handler.commandvalidation

import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.applicationservice.handler.commands.ArtCommand
import com.jarqprog.artapi.applicationservice.handler.commands.ChangeResource
import com.jarqprog.artapi.applicationservice.handler.commands.CreateArt
import com.jarqprog.artapi.applicationservice.handler.exceptions.CommandProcessingFailure
import reactor.core.publisher.Mono

class CommandValidator : CommandValidation {

    override fun validate(command: ArtCommand, history: ArtHistory, currentState: ArtAggregate):
            Mono<ArtCommand> {
        return when (command) {
            is CreateArt -> process(command, history, currentState)
            is ChangeResource -> process(command, history, currentState)
            else -> Mono.error(CommandProcessingFailure("invalid unhandled command: $command"))
        }
    }

    private fun process(command: CreateArt, history: ArtHistory, currentState: ArtAggregate):
            Mono<ArtCommand> {

        return Mono.fromCallable {
            raiseFailureIf(command.version() != 0, "Incorrect version for $command. " +
                    "Expected version: 0")
            validateHistory(command, history)
            validateArtIdEquality(command, currentState)
            command
        }
    }

    private fun process(command: ChangeResource, history: ArtHistory, currentState: ArtAggregate):
            Mono<ArtCommand> {
        return Mono.fromCallable {
            validateHistory(command, history)
            validateArtIdEquality(command, currentState)
            validateCommandVersionOnUpdate(command, currentState)
            raiseFailureIf(command.resource() == currentState.resource(),
                    "resource url for $command is the same - event not created")
            command
        }
    }

    companion object {
        private fun validateArtIdEquality(command: ArtCommand, currentState: ArtAggregate) {
            val commandArtUuid = command.artId()
            val artUuid = currentState.identifier()
            raiseFailureIf(commandArtUuid != artUuid,
                    "invalid art identifier for $command, expected id: $artUuid")
        }

        private fun validateCommandVersionOnUpdate(command: ArtCommand, currentState: ArtAggregate) {
            val expectedVersion = currentState.version()
            raiseFailureIf(command.version() != expectedVersion,
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
    }
}