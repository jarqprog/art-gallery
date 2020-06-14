package com.jarqprog.artapi.command.api.commanddispatching

import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.command.api.CommandDispatching
import com.jarqprog.artapi.command.api.CommandValidation
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.commands.ArtCommand
import com.jarqprog.artapi.domain.commands.ChangeResource
import com.jarqprog.artapi.domain.commands.CreateArt
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.events.ResourceChanged
import com.jarqprog.artapi.command.api.exceptions.CommandProcessingFailure

import reactor.core.publisher.Mono

class CommandDispatcher(private val validation: CommandValidation) : CommandDispatching {

    override fun dispatch(command: ArtCommand, history: ArtHistory): Mono<ArtEvent> {

        return when (command) {
            is CreateArt -> process(command, history)
            is ChangeResource -> process(command, history)
            else -> Mono.error(CommandProcessingFailure("invalid unhandled command: $command"))
        }
    }

    private fun process(command: CreateArt, history: ArtHistory): Mono<ArtEvent> {
        val initialState = ArtAggregate.initialState(command.artId())
        return validation.validate(command, history, initialState)
                .map { ArtCreated.from(command) }
    }

    private fun process(command: ChangeResource, history: ArtHistory): Mono<ArtEvent> {
        val currentState = ArtAggregate.replayAll(history)
        return validation.validate(command, history, currentState)
                .map { ResourceChanged.from(command) }
    }
}