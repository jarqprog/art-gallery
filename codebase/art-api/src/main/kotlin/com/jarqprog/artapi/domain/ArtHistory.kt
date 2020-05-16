package com.jarqprog.artapi.domain

import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.vo.Identifier
import java.time.Instant
import java.util.stream.Collectors

private val INITIAL_TIMESTAMP: Instant = Instant.MIN

class ArtHistory(

        private val artId: Identifier,
        events: List<ArtEvent>

) {
    private val history: List<ArtEvent> = events
            .stream()
            .sorted(Comparator.comparing(ArtEvent::timestamp))
            .collect(Collectors.toList())

    private val version: Int = history.size.minus(1)
    private val timestamp: Instant = resolveTimestamp()

    fun artId() = artId
    fun version() = version
    fun timestamp() = timestamp
    fun events() = history
    fun isEmpty() = history.isEmpty()
    fun isNotEmpty() = !isEmpty()
    fun size() = history.size
    override fun toString() = "history for artId: $artId, version: $version, timestamp: $timestamp, size: ${size()}"

    companion object Factory {
        fun initialize(artId: Identifier): ArtHistory = ArtHistory(artId, emptyList())
    }

    private fun resolveTimestamp(): Instant {
        return when (history.isEmpty()) {
            true -> INITIAL_TIMESTAMP
            else -> history.last().timestamp()
        }
    }
}