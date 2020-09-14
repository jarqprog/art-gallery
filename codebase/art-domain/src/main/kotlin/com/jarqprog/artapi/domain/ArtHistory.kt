package com.jarqprog.artapi.domain

import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.vo.Identifier
import java.time.Instant
import java.util.stream.Collectors
import kotlin.Comparator

class ArtHistory private constructor(

        private val artId: Identifier,
        private val snapshot: ArtAggregate,
        events: List<ArtEvent>

) {
    private val history: List<ArtEvent> = events
            .stream()
            .filter { event -> event.timestamp().isAfter(snapshot.timestamp()) }
            .sorted(Comparator.comparing(ArtEvent::timestamp))
            .collect(Collectors.toList())

    private val version: Int = resolveVersion()
    private val timestamp: Instant = resolveTimestamp()

    fun artId() = artId
    fun version() = version
    fun timestamp() = timestamp
    fun events() = history
    fun isEmpty() = history.isEmpty()
    fun isNotEmpty() = !isEmpty()
    fun size() = history.size
    fun snapshot() = snapshot

    override fun toString() = "history for artId: $artId, version: $version, timestamp: $timestamp, size: ${size()}"

    companion object Factory {
        fun initialize(artId: Identifier) = ArtHistory(artId, ArtAggregate.initialState(artId), emptyList())

        fun withSnapshot(snapshot: ArtAggregate, events: List<ArtEvent> = emptyList()) = ArtHistory(
                snapshot.identifier(), snapshot, events
        )

        fun withEvents(events: List<ArtEvent>): ArtHistory {
            if (events.isEmpty()) throw IllegalArgumentException("cannot create history without events")
            val artId = events.first().artId()
            return ArtHistory(artId, ArtAggregate.initialState(artId), events)
        }
    }

    private fun resolveVersion(): Int {
        return when (history.isEmpty()) {
            true -> snapshot.version()
            else -> history.last().version()
        }
    }

    private fun resolveTimestamp(): Instant {
        return when (history.isEmpty()) {
            true -> snapshot.timestamp()
            else -> history.last().timestamp()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArtHistory) return false

        if (artId != other.artId) return false
        if (version != other.version) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = artId.hashCode()
        result = 31 * result + version
        result = 31 * result + timestamp.hashCode()
        return result
    }
}