package com.jarqprog.artapi.read.database

import com.jarqprog.artapi.write.dto.CommentDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.ArrayList

private val CACHE = ConcurrentHashMap<UUID, CachedComment>()
private var isCleanInProgress =  AtomicBoolean(false)

class CommentCache(
        private val maxCacheSize: Int = 100,
        private val acceptableAgeInMinutes: Long = 2L
): ReadDatabaseFacade, LocalCache<UUID,CommentDTO> {

    override fun remember(uuid: UUID, optionalValue: Optional<CommentDTO>) {
        CACHE[uuid] = CachedComment(optionalValue)
        validateCache()
    }

    override fun load(uuid: UUID): Optional<CommentDTO> {
        return Optional.ofNullable(CACHE[uuid])
                .filter { cachedComment -> cachedComment.isNotOlderThan(acceptableAgeInMinutes) }
                .filter { cachedComment -> cachedComment.comment.isPresent }
                .map { cachedComment -> cachedComment.comment }
                .orElseGet { Optional.empty() }
    }

    private fun validateCache() {
        if (CACHE.size > maxCacheSize) {
            if (!isCleanInProgress.get()) cleanUp()
            // todo: modify cache size else maxCacheSize
        }
    }

    private fun cleanUp() {
        runBlocking {
            launch(Dispatchers.Default) {
                isCleanInProgress.set(true)
                val toRemove = ArrayList<UUID>()
                CACHE.forEach { (uuid,comment) -> if (comment.isOlderThan(acceptableAgeInMinutes)) toRemove.add(uuid) }
                toRemove.forEach { uuid -> CACHE.remove(uuid) }
                isCleanInProgress.set(false)
            }
        }
    }
}