package com.jarqprog.artapi.applicationservice.dispatching

import com.jarqprog.artapi.applicationservice.ProcessingResult
import com.jarqprog.artapi.domain.art.ArtAggregate
import com.jarqprog.artapi.domain.CommandValidation
import com.jarqprog.artapi.domain.art.ArtHistory
import com.jarqprog.artapi.applicationservice.commands.ArtCommand
import com.jarqprog.artapi.applicationservice.commands.ChangeResource
import com.jarqprog.artapi.applicationservice.commands.CreateArt
import com.jarqprog.artapi.applicationservice.exceptions.CommandProcessingFailure
import com.jarqprog.artapi.domain.CommandDispatching
import com.jarqprog.artapi.domain.events.ArtEvent

import reactor.core.publisher.Mono

class CommandDispatcher(private val validation: CommandValidation) : CommandDispatching {

    override fun dispatch(command: ArtCommand, history: ArtHistory): Mono<ProcessingResult> {
        return when (command) {
            is CreateArt -> process(command, history)
            is ChangeResource -> process(command, history)
            else -> Mono.error(CommandProcessingFailure("invalid unhandled command: $command"))
        }
    }

    private fun process(command: CreateArt, history: ArtHistory): Mono<ProcessingResult> {
        val initialState = ArtAggregate.initialState(command.artId())
        return validation.validate(command, history, initialState)
                .map { command.asEvent() }
                .map { event -> asProcessingResult(event, initialState) }
    }

    private fun process(command: ChangeResource, history: ArtHistory): Mono<ProcessingResult> {
        val currentState = ArtAggregate.replayAll(history)
        return validation.validate(command, history, currentState)
                .map { command.asEvent() }
                .map { event -> asProcessingResult(event, currentState) }
    }

    private fun asProcessingResult(artEvent: ArtEvent, previousState: ArtAggregate): ProcessingResult {
        return ProcessingResult(
                artEvent,
                ArtAggregate.replayAll(
                        ArtHistory.with(
                                artEvent.artId(),
                                listOf(artEvent.asDescriptor()),
                                previousState.asDescriptor()
                        )
                )
        )
    }
}
