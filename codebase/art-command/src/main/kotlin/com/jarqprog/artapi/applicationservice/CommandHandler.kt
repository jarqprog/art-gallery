package com.jarqprog.artapi.applicationservice


import com.jarqprog.artapi.applicationservice.dispatching.CommandDispatching
import com.jarqprog.artapi.applicationservice.commands.ArtCommand
import com.jarqprog.artapi.applicationservice.publishing.EventPublishing
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStore
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

class CommandHandler(

        private val commandDispatching: CommandDispatching,
        private val eventPublishing: EventPublishing,
        private val eventStore: EventStore

) : CommandHandling {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun handle(command: ArtCommand): Mono<Void> {
        return eventStore.load(command.artId())
                .doOnNext { artCommand -> logger.debug("Processing $artCommand") }
                .switchIfEmpty(
                        Mono.just(
                            ArtHistory.initialize(command.artId)
                        )
                )
                .doOnNext { history -> logger.debug("Found history $history") }
                .flatMap { history -> commandDispatching.dispatch(command, history) }
                .flatMap(eventPublishing::publish)
    }
}
