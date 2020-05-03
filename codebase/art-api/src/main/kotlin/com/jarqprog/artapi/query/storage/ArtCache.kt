package com.jarqprog.artapi.query.storage

import com.jarqprog.artapi.query.api.QueryFacade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.ArrayList

private val CACHE = ConcurrentHashMap<UUID, CachedArt>()
private var isCleanInProgress =  AtomicBoolean(false)

class ArtCache(
        private val maxCacheSize: Int = 100,
        private val acceptableAgeInMinutes: Long = 2L

): QueryFacade, LocalCache<UUID, String> {

    override fun remember(uuid: UUID, optionalValue: Optional<String>) {
        CACHE[uuid] = CachedArt(optionalValue)
        validateCache()
    }

    override fun load(uuid: UUID): Optional<String> {
        return Optional.ofNullable(CACHE[uuid])
                .filter { cachedArt -> cachedArt.isNotOlderThan(acceptableAgeInMinutes) }
                .filter { cachedArt -> cachedArt.art.isPresent }
                .map { cachedArt -> cachedArt.art }
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
                CACHE.forEach { (uuid,art) -> if (art.isOlderThan(acceptableAgeInMinutes)) toRemove.add(uuid) }
                toRemove.forEach { uuid -> CACHE.remove(uuid) }
                isCleanInProgress.set(false)
            }
        }
    }
}