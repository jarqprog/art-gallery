package com.jarqprog.artapi.domain.art

import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.events.EventDescriptor
import com.jarqprog.artapi.domain.events.TransformableEvent
import com.jarqprog.artapi.domain.vo.Identifier
import java.time.Instant
import kotlin.Comparator
import kotlin.streams.toList

class ArtHistory private constructor(

        private val artId: Identifier,
        private val snapshot: ArtAggregate,
        events: List<ArtEvent>

) {
    private val history: List<ArtEvent> = events
            .stream()
            .filter { event -> event.timestamp().isAfter(snapshot.timestamp()) }
            .sorted(Comparator.comparing(ArtEvent::timestamp))
            .toList()

    private val version: Int = resolveVersion()
    private val timestamp: Instant = resolveTimestamp()

    fun artId() = artId
    fun version() = version
    fun timestamp() = timestamp
    fun events() = history
    fun isEmpty() = history.isEmpty()
    fun isNotEmpty() = history.isNotEmpty()
    fun snapshot() = snapshot

    override fun toString() = "history for artId: $artId, version: $version, timestamp: $timestamp, size: ${history.size}"

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

    companion object Factory {

        fun with(artId: Identifier) = ArtHistory(artId, ArtAggregate.initialState(artId), emptyList())

        fun with(artId: Identifier, eventDescriptors: List<EventDescriptor>): ArtHistory {
            return when(eventDescriptors.isEmpty()) {
                true -> with(artId)
                else -> ArtHistory(
                        artId,
                        ArtAggregate.initialState(artId),
                        eventDescriptors.stream()
                                .map { descriptor -> TransformableEvent.fromDescriptor(descriptor) }
                                .toList()
                )
            }
        }

        fun with(artId: Identifier, eventDescriptors: List<EventDescriptor>, descriptor: ArtDescriptor): ArtHistory {
            return withSnapshot(artId, eventDescriptors, TransformableArt.fromDescriptor(descriptor))
        }

        fun from(artId: Identifier, eventDescriptors: List<EventDescriptor>, stateAt: Instant): ArtHistory {
            val filtered = eventDescriptors.stream()
                    .filter { eventDescriptor -> eventDescriptor.isNotLaterThan(stateAt) }
                    .toList()
            return with(artId, filtered)
        }

        private fun withSnapshot(artId: Identifier, eventDescriptors: List<EventDescriptor>, snapshot: ArtAggregate): ArtHistory {
            return ArtHistory(
                    artId,
                    snapshot,
                    eventDescriptors.stream()
                            .filter { descriptor -> !descriptor.isNotLaterThan(snapshot.timestamp()) }
                            .map { descriptor -> TransformableEvent.fromDescriptor(descriptor) }
                            .toList()
            )
        }
    }
}