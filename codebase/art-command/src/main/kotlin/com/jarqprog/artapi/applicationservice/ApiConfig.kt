package com.jarqprog.artapi.applicationservice

import com.jarqprog.artapi.applicationservice.handler.commanddispatching.CommandDispatcher
import com.jarqprog.artapi.applicationservice.handler.CommandHandler
import com.jarqprog.artapi.applicationservice.handler.CommandHandling
import com.jarqprog.artapi.applicationservice.handler.commandvalidation.CommandValidator
import com.jarqprog.artapi.applicationservice.handler.eventpublishing.EventPropagator
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiConfig {

    @Bean
    fun commandHandling(@Autowired eventStore: EventStore): CommandHandling {
        return CommandHandler(
                CommandDispatcher(CommandValidator()),
                EventPropagator(eventStore),
                eventStore
        )
    }
}