package com.jarqprog.artapi.command.api.commandhandling


import arrow.core.getOrHandle
import com.jarqprog.artapi.command.api.CommandDispatching
import com.jarqprog.artapi.command.api.CommandHandling
import com.jarqprog.artapi.command.api.EventPublishing
import com.jarqprog.artapi.domain.*
import com.jarqprog.artapi.command.api.commands.ArtCommand
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStore

import java.util.*

class CommandHandler(

        private val commandDispatching: CommandDispatching,
        private val eventPublishing: EventPublishing,
        private val eventStore: EventStore

) : CommandHandling {

    override fun handle(command: ArtCommand): Optional<Throwable> {
        return eventStore.load(command.artId())
                .map { optionalHistory ->
                    optionalHistory
                            .or { Optional.of(ArtHistory.initialize(command.artId)) }
                            .map { history -> commandDispatching.dispatch(command, history) }
                            .flatMap { failureOrEvent ->
                                failureOrEvent
                                        .map(eventPublishing::publish)
                                        .getOrHandle { failure -> Optional.of(failure) }
                            }
                }
                .getOrHandle { failure -> Optional.of(failure) }
    }
}
