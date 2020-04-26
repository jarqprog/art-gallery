package com.jarqprog.artapi.read.api

import com.jarqprog.artapi.read.api.db.CommentReadRepository
import com.jarqprog.artapi.read.api.db.LocalCache
import java.util.*


class ReadPlugin(
        private val database: CommentReadRepository,
        private val cache: LocalCache<UUID, String>): ReadFacade {

    override fun load(uuid: UUID): Optional<String> {
        return Optional.of(cache.load(uuid))
                .filter { art -> art.isPresent }
                .orElseGet {
                    val art = database.findByUuid(uuid)
                    cache.remember(uuid, art)
                    art
                }
    }
}