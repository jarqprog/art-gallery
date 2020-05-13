package com.jarqprog.artapi.querydepricated.storage

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.jarqprog.artapi.querydepricated.api.QueryRepository
import com.jarqprog.artapi.querydepricated.exceptions.DatabaseFailure
import org.davidmoten.rx.jdbc.Database
import org.postgresql.util.PGobject
import java.util.*


private const val FIND_ONE_QUERY = "SELECT ART FROM JSON_ART WHERE UUID=?"
private val MAPPER = ObjectMapper().registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)


class RxRepositoryWithCache(private val database: Database,
                            private val cache: LocalCache<UUID, String>) : QueryRepository {

    override fun findByUuid(uuid: UUID): Optional<String> = Optional.of(cache.load(uuid))
            .filter { art -> art.isPresent }
            .orElseGet {
                val art = fromDB(uuid)
                cache.remember(uuid, art)
                art
            }

    private fun fromDB(uuid: UUID): Optional<String> = database.select(FIND_ONE_QUERY)
            .parameter(uuid)
            .getAsOptional(PGobject::class.java)
            .doOnError { ex -> DatabaseFailure(ex) }
            .defaultIfEmpty(Optional.empty())
            .blockingFirst()
            .map(PGobject::getValue)
            .map(this::asJson)

    private fun asJson(rawJson: String): String {
        val json = MAPPER.readValue(rawJson, object : TypeReference<Map<String, Any>>() {})
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(json)
    }
}
