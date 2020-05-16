package com.jarqprog.artapi.command.ports.outgoing

import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStorage
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStore
import com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.sql.ArtStreamRepository
import com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.sql.EventStreamJDBC
import com.jarqprog.artapi.command.ports.outgoing.projection.ProjectionHandler
import com.jarqprog.artapi.command.ports.outgoing.projection.ProjectionHandling
import com.jarqprog.artapi.command.ports.outgoing.projection.dao.sql.ArtProjectionRepository
import com.jarqprog.artapi.command.ports.outgoing.projection.dao.sql.ProjectionJDBC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OutgoingConfig {

    @Bean
    fun eventStore(@Autowired artStreamRepository: ArtStreamRepository): EventStore {
        return EventStorage(EventStreamJDBC(artStreamRepository))
    }

    @Bean
    fun projectionHandling(
            @Autowired eventStore: EventStore,
            @Autowired artProjectionRepository: ArtProjectionRepository
    ): ProjectionHandling {
        return ProjectionHandler(eventStore, ProjectionJDBC(artProjectionRepository))
    }
}
