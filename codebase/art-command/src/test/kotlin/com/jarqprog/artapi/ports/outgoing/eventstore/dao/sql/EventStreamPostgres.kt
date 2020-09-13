package com.jarqprog.artapi.ports.outgoing.eventstore.dao.sql

import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.ports.outgoing.eventstore.EventStreamDatabase
import com.jarqprog.artapi.ports.outgoing.eventstore.entity.EventDescriptor
import org.slf4j.LoggerFactory
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria.where
import reactor.core.publisher.Mono

class EventStreamPostgres(private val databaseClient : DatabaseClient) : EventStreamDatabase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun historyExistsById(artId: Identifier): Mono<Boolean> {
        return databaseClient
                .execute("SELECT $VERSION FROM $EVENT_TABLE WHERE $ART_ID = '${artId.uuid()}'")
                .fetch()
                .first()
                .map { true }
                .defaultIfEmpty(false)
                .doOnNext { result -> logger.debug("Checked if history exists for $artId: $result") }
    }

    override fun streamVersion(artId: Identifier): Mono<Int> {
        return databaseClient
                .execute("SELECT MAX($VERSION) FROM $EVENT_TABLE WHERE $ART_ID = '${artId.uuid()}'")
                .map { row,_ -> row.get(MAX_KEY, Integer::class.java) }
                .first()
                .map { integer -> integer!!.toInt() }
                .doOnNext { version -> logger.debug("fetched version $version for $artId") }
    }

    override fun save(event: EventDescriptor): Mono<Void> = databaseClient
            .insert()
            .into(EventDescriptor::class.java)
            .using(event)
            .then()
            .doOnNext { logger.debug("saved $event") }

    override fun load(artId: Identifier): Mono<List<EventDescriptor>> = databaseClient
            .select()
            .from(EventDescriptor::class.java)
            .matching(where(ART_ID).`is`(artId.uuid()))
            .fetch()
            .all()
            .collectList()
            .doOnNext { events -> logger.debug("Fetched events for $artId: $events") }

    private companion object Postgres {

        private const val EVENT_TABLE = "event_descriptor"
        private const val ART_ID = "art_id"
        private const val VERSION = "version"
        private const val MAX_KEY = "max"

    }
}