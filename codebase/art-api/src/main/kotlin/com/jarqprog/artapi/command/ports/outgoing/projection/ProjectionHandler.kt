package com.jarqprog.artapi.command.ports.outgoing.projection

import com.jarqprog.artapi.domain.Art
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStore
import com.jarqprog.artapi.command.ports.outgoing.projection.dto.ArtDto
import com.jarqprog.artapi.command.ports.outgoing.projection.entity.ArtProjection
import com.jarqprog.artapi.command.ports.outgoing.projection.exception.ProjectionHandlingFailure
import io.vavr.control.Try

import java.util.*

class ProjectionHandler(
        private val eventStore: EventStore,
        private val projectionDatabase: ProjectionDatabase
) : ProjectionHandling {

    override fun handle(artIdentifier: Identifier): Optional<ProjectionHandlingFailure> {
        return Try.run {
            eventStore.load(artIdentifier)
                    .map { optionalHistory ->
                        optionalHistory
                                .map { history -> Art.replayAll(artIdentifier, history) }
                                .map { art -> ArtDto.fromArt(art) }
                                .map { dto -> ArtProjection.fromDto(dto) }
                                .map { projection -> projectionDatabase.save(projection) }
//                                .orElseGet { () -> ProjectionHandlingFailure("History not fetched") }
                    }
        }
                .fold(
                        { failure -> Optional.of(ProjectionHandlingFailure.fromException(failure)) },
                        { Optional.empty() }
                )
    }
}
