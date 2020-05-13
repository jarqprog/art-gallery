package com.jarqprog.artapi.command.api.commandhandling


import arrow.core.getOrHandle
import com.jarqprog.artapi.command.api.CommandDispatching
import com.jarqprog.artapi.command.api.CommandHandling
import com.jarqprog.artapi.command.api.EventPublishing
import com.jarqprog.artapi.command.domain.*
import com.jarqprog.artapi.command.domain.commands.ArtCommand
import com.jarqprog.artapi.command.api.exceptions.CommandProcessingFailure
import com.jarqprog.artapi.command.ports.outgoing.EventStore

import java.util.*

class CommandHandler(

        private val commandDispatching: CommandDispatching,
        private val eventPublishing: EventPublishing,
        private val eventStore: EventStore

) : CommandHandling {

    override fun handle(command: ArtCommand): Optional<CommandProcessingFailure> {
        return eventStore.load(command.artId())
                .or { Optional.of(ArtHistory.initialize(command.artId)) }
                .map { history -> commandDispatching.dispatch(command, history) }
                .flatMap { failureOrEvent ->
                    failureOrEvent
                            .map(eventPublishing::publish)
                            .map { optionalFailure ->
                                optionalFailure
                                        .map { failure -> CommandProcessingFailure.fromThrowable(failure) }
                            }.getOrHandle { failure -> Optional.of(failure) }
                }
    }
}
