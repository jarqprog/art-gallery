package com.jarqprog.artapi.read.database

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.jarqprog.artapi.read.exceptions.DatabaseFailure
import com.jarqprog.artapi.read.exceptions.NotFound
import com.jarqprog.artapi.write.dto.CommentDTO
import org.davidmoten.rx.jdbc.Database
import org.postgresql.util.PGobject
import java.util.*


private const val FIND_ONE_QUERY = "SELECT comment FROM json_comment WHERE UUID=?"
private val MAPPER = ObjectMapper().registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

class CommentReadRepository(private val database: Database) {

    fun findByUuid(uuid: UUID): Optional<CommentDTO> = database.select(FIND_ONE_QUERY)
            .parameter(uuid)
            .getAsOptional(PGobject::class.java)
            .doOnError { ex -> DatabaseFailure(ex) }
            .defaultIfEmpty(Optional.empty())
            .blockingFirst()
            .map(PGobject::getValue)
            .map(this::asComment)

    private fun asComment(rawJson: String) = MAPPER.readValue(rawJson, CommentDTO::class.java)
}