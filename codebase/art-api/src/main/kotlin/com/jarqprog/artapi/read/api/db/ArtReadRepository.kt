package com.jarqprog.artapi.read.api.db

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.jarqprog.artapi.read.exceptions.DatabaseFailure
import org.davidmoten.rx.jdbc.Database
import org.postgresql.util.PGobject
import java.util.*


private const val FIND_ONE_QUERY = "SELECT ART FROM JSON_ART WHERE UUID=?"
private val MAPPER = ObjectMapper().registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

class CommentReadRepository(private val database: Database) {

    fun findByUuid(uuid: UUID): Optional<String> = database.select(FIND_ONE_QUERY)
            .parameter(uuid)
            .getAsOptional(PGobject::class.java)
            .doOnError { ex -> DatabaseFailure(ex) }
            .defaultIfEmpty(Optional.empty())
            .blockingFirst()
            .map(PGobject::getValue)
            .map(this::asJson)

    private fun asJson(rawJson: String): String {
        val json = MAPPER.readValue(rawJson, object: TypeReference<Map<String, Any>>() {})
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(json)
    }

}
