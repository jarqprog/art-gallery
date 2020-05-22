package com.jarqprog.artapi.command.ports.outgoing

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStorage
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStore
import com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.sql.HistoryRepository
import com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.sql.EventStreamJDBC
import com.jarqprog.artapi.command.ports.outgoing.projection.ProjectionHandler
import com.jarqprog.artapi.command.ports.outgoing.projection.ProjectionHandling
import com.jarqprog.artapi.command.ports.outgoing.projection.dao.sql.ArtProjectionRepository
import com.jarqprog.artapi.command.ports.outgoing.projection.dao.sql.ProjectionJDBC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

internal val MAPPER = jacksonObjectMapper()
        .registerModule(JavaTimeModule())
        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)

@Configuration
class OutgoingConfig {

    @Bean
    fun eventStore(@Autowired streamRepository: HistoryRepository): EventStore {
        return EventStorage(EventStreamJDBC(streamRepository))
    }

    @Bean
    fun projectionHandling(
            @Autowired eventStore: EventStore,
            @Autowired artProjectionRepository: ArtProjectionRepository
    ): ProjectionHandling {
        return ProjectionHandler(eventStore, ProjectionJDBC(artProjectionRepository))
    }
}
