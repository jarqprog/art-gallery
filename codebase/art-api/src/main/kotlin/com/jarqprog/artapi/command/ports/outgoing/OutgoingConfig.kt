package com.jarqprog.artapi.command.ports.outgoing

import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStorage
import com.jarqprog.artapi.command.ports.outgoing.eventstore.sql.ArtStreamRepository
import com.jarqprog.artapi.command.ports.outgoing.eventstore.sql.EventStreamJDBC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OutgoingConfig {

    @Bean
    fun eventStore(@Autowired artStreamRepository: ArtStreamRepository): EventStore {
        return EventStorage(EventStreamJDBC(artStreamRepository))
    }
}