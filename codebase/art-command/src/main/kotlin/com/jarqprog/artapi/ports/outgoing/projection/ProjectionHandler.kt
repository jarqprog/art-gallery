package com.jarqprog.artapi.ports.outgoing.projection

//class ProjectionHandler(
//        private val eventStore: EventStore,
//        private val projectionDatabase: ProjectionDatabase
//) : ProjectionHandling {
//
//    private val logger = LoggerFactory.getLogger(javaClass)
//
//    override fun handle(artIdentifier: Identifier): Optional<ProjectionHandlingFailure> {
//        return Try.run {
//            logger.info("about to update projection for art identifier: $artIdentifier")
//            eventStore.load(artIdentifier)
//                    .map { optionalHistory ->
//                        optionalHistory
//                                .map { history -> com.jarqprog.artapi.command.domain.ArtAggregate.replayAll( history) }
//                                .map { art -> ArtDto.fromArt(art) }
//                                .map { dto -> ArtProjection.fromDto(dto) }
//                                .map { projection -> projectionDatabase.save(projection) }
//                                .also { logger.info("updated projection for art identifier: $artIdentifier") }
//                    }
//        }
//                .fold(
//                        { failure -> Optional.of(ProjectionHandlingFailure.fromException(failure)) },
//                        { Optional.empty() }
//                )
//    }
//}
