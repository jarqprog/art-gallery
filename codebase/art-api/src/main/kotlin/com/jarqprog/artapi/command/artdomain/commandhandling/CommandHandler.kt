package com.jarqprog.artapi.command.artdomain.commandhandling


import arrow.core.getOrHandle
import com.jarqprog.artapi.command.artdomain.*
import com.jarqprog.artapi.command.artdomain.commands.ArtCommand
import com.jarqprog.artapi.command.artdomain.exceptions.CommandProcessingFailure

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
