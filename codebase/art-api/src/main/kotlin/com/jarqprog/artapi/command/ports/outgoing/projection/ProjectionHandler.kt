package com.jarqprog.artapi.command.ports.outgoing.projection

import com.jarqprog.artapi.domain.Art
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.EventStore
import com.jarqprog.artapi.command.ports.outgoing.projection.dto.ArtDto
import com.jarqprog.artapi.command.ports.outgoing.projection.entity.ArtProjection
import com.jarqprog.artapi.command.ports.outgoing.projection.exception.ProjectionHandlingFailure
import io.vavr.control.Try
import org.slf4j.LoggerFactory

import java.util.*

class ProjectionHandler(
        private val eventStore: EventStore,
        private val projectionDatabase: ProjectionDatabase
) : ProjectionHandling {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun handle(artIdentifier: Identifier): Optional<ProjectionHandlingFailure> {
        return Try.run {
            logger.info("about to update projection for art identifier: $artIdentifier")
            eventStore.load(artIdentifier)
                    .map { optionalHistory ->
                        optionalHistory
                                .map { history -> Art.replayAll(artIdentifier, history) }
                                .map { art -> ArtDto.fromArt(art) }
                                .map { dto -> ArtProjection.fromDto(dto) }
                                .map { projection -> projectionDatabase.save(projection) }
                                .also { logger.info("updated projection for art identifier: $artIdentifier") }
                    }
        }
                .fold(
                        { failure -> Optional.of(ProjectionHandlingFailure.fromException(failure)) },
                        { Optional.empty() }
                )
    }
}
