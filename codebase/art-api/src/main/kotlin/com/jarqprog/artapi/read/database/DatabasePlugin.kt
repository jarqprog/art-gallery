package com.jarqprog.artapi.read.database

import com.jarqprog.artapi.write.dto.CommentDTO
import java.util.*


class DatabasePlugin(
        private val database: CommentReadRepository,
        private val cache: LocalCache<UUID,CommentDTO>): ReadDatabaseFacade {

    override fun load(uuid: UUID): Optional<CommentDTO> {
        return Optional.of(cache.load(uuid))
                .filter { optionalComment -> optionalComment.isPresent }
                .orElseGet {
                    val optionalComment = database.findByUuid(uuid)
                    cache.remember(uuid, optionalComment)
                    optionalComment
                }
    }
}