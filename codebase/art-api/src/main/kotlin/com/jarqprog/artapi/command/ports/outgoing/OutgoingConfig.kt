package com.jarqprog.artapi.command.ports.outgoing

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStorage
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStore
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStreamDatabase
import com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.inmemory.InMemorySnapshotDatabase

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ConcurrentHashMap

internal val MAPPER = jacksonObjectMapper()
        .registerModule(JavaTimeModule())
        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)

@Configuration
class OutgoingConfig {

    @Bean
    fun eventStore(eventStreamPostgres: EventStreamDatabase): EventStore {
        return EventStorage(eventStreamPostgres, InMemorySnapshotDatabase(ConcurrentHashMap()))
    }



//    @Bean
//    fun projectionHandling(
//            @Autowired eventStore: EventStore,
//            @Autowired artProjectionRepository: ArtProjectionRepository
//    ): ProjectionHandling {
//        return ProjectionHandler(eventStore, ProjectionJDBC(artProjectionRepository))
//    }
}
