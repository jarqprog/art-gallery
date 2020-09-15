package com.jarqprog.artapi.ports.outgoing.eventstore.database

import com.jarqprog.artapi.domain.art.ArtDescriptor
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.ports.outgoing.eventstore.SnapshotRepository
import org.slf4j.LoggerFactory
import org.springframework.data.r2dbc.core.DatabaseClient
import reactor.core.publisher.Mono

class SnapshotSql(private val databaseClient : DatabaseClient) : SnapshotRepository {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun save(art: ArtDescriptor): Mono<Void> = databaseClient
            .insert()
            .into(ArtDescriptor::class.java)
            .using(art)
            .then()
            .doOnSuccess { logger.debug("saved $art") }

    override fun loadLatest(artId: Identifier): Mono<ArtDescriptor> {
        return Mono.empty()
    }

    private companion object Postgres {
        private const val TABLE = "art_descriptor"
        private const val ART_ID = "art_id"
        private const val VERSION = "version"
    }
}
