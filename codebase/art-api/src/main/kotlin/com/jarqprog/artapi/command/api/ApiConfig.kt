package com.jarqprog.artapi.command.api

import com.jarqprog.artapi.command.api.commanddispatching.CommandDispatcher
import com.jarqprog.artapi.command.api.commandhandling.CommandHandler
import com.jarqprog.artapi.command.api.commandvalidation.CommandValidator
import com.jarqprog.artapi.command.api.eventpublishing.EventPropagator
import com.jarqprog.artapi.command.ports.outgoing.EventStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiConfig {

    @Bean
    fun commandHandling(
            @Autowired eventStore: EventStore
    ) : CommandHandling {
        return CommandHandler(
                CommandDispatcher(CommandValidator()),
                EventPropagator(eventStore),
                eventStore
        )
    }

}