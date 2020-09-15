package com.jarqprog.artapi.ports.outgoing

import com.jarqprog.artapi.ports.outgoing.eventstore.EventStorage
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStore
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStreamRepository

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.jarqprog.artapi.ports.outgoing.eventstore.SnapshotRepository

@Configuration
class OutgoingConfig {

    @Bean
    fun eventStore(eventStreamRepository: EventStreamRepository, snapshotRepository: SnapshotRepository): EventStore {
        return EventStorage(eventStreamRepository, snapshotRepository)
    }


//    @Bean
//    fun projectionHandling(
//            @Autowired eventStore: EventStore,
//            @Autowired artProjectionRepository: ArtProjectionRepository
//    ): ProjectionHandling {
//        return ProjectionHandler(eventStore, ProjectionJDBC(artProjectionRepository))
//    }
}
