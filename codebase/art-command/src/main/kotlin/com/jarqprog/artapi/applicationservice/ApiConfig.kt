package com.jarqprog.artapi.applicationservice

import com.jarqprog.artapi.applicationservice.dispatching.CommandDispatcher
import com.jarqprog.artapi.applicationservice.validation.CommandValidator
import com.jarqprog.artapi.applicationservice.publishing.ChangesPropagator
import com.jarqprog.artapi.domain.CommandHandling
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
                ChangesPropagator(eventStore),
                eventStore
        )
    }
}