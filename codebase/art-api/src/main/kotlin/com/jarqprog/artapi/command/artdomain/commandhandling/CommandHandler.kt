package com.jarqprog.artapi.command.artdomain.commandhandling


import arrow.core.flatMap
import arrow.core.getOrHandle
import com.jarqprog.artapi.command.artdomain.CommandDispatching
import com.jarqprog.artapi.command.artdomain.CommandHandling
import com.jarqprog.artapi.command.artdomain.EventPublishing
import com.jarqprog.artapi.command.artdomain.EventStore
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
                .or { Optional.of(emptyList()) }
                .map { history -> commandDispatching.dispatch(command, history) }
                .flatMap { failureOrEvent ->
                    failureOrEvent
                            .map(eventPublishing::publish)
                            .map { optionalFailure ->
                                optionalFailure
                                        .map { failure -> CommandProcessingFailure.fromThrowable(failure) }
                            }.getOrHandle { fail -> Optional.of(fail) }
                }
    }
}
