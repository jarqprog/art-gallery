package com.jarqprog.artapi.domain.art

import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.events.ResourceChanged
import com.jarqprog.artapi.domain.exceptions.EventProcessingFailure
import com.jarqprog.artapi.domain.vo.Author
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.domain.vo.User

import java.time.Instant
import java.util.*

class ArtAggregate private constructor(
        private val identifier: Identifier,
        private val version: Int,
        private val timestamp: Instant,
        private val author: Author,
        private val resource: Resource,
        private val addedBy: User,
        private val genre: ArtGenre,
        private val status: ArtStatus
): TransformableArt {
    fun identifier() = identifier
    fun version() = version
    fun timestamp() = timestamp
    fun author() = author
    fun resource() = resource
    fun addedBy() = addedBy
    fun genre() = genre
    fun status() = status

    private fun applyEvent(event: ArtEvent): ArtAggregate {
        return when (event) {
            is ArtCreated -> apply(event)
            is ResourceChanged -> apply(event)
            else -> throw EventProcessingFailure("invalid unprocessed event: $event")
        }
    }

    private fun apply(event: ArtCreated): ArtAggregate {
        return ArtAggregate(
                identifier,
                event.version(),
                event.timestamp(),
                event.author(),
                event.resource(),
                event.addedBy(),
                event.artGenre(),
                event.artStatus()
        )
    }

    private fun apply(event: ResourceChanged): ArtAggregate {
        return ArtAggregate(
                identifier,
                event.version(),
                event.timestamp(),
                author,
                event.resource(),
                addedBy,
                genre,
                status
        )
    }

    override fun toString(): String {
        return "ArtAggregate(identifier=$identifier, version=$version, timestamp=$timestamp, author=$author, resource=$resource, addedBy=$addedBy, genre=$genre, status=$status)"
    }

    fun asDescriptor(): ArtDescriptor = ArtDescriptor(
            UUID.randomUUID(),
            this.identifier.uuid(),
            this.version,
            this.timestamp,
            asJson()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArtAggregate) return false

        if (identifier != other.identifier) return false
        if (version != other.version) return false
        if (timestamp != other.timestamp) return false
        if (author != other.author) return false
        if (resource != other.resource) return false
        if (addedBy != other.addedBy) return false
        if (genre != other.genre) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = identifier.hashCode()
        result = 31 * result + version
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + resource.hashCode()
        result = 31 * result + addedBy.hashCode()
        result = 31 * result + genre.hashCode()
        result = 31 * result + status.hashCode()
        return result
    }


    companion object Factory {

        const val INITIAL_VERSION: Int = -1

        fun initialState(identifier: Identifier): ArtAggregate {
            return ArtAggregate(
                    identifier,
                    INITIAL_VERSION,
                    Instant.MIN,
                    Author(),
                    Resource(),
                    User(),
                    ArtGenre.UNDEFINED,
                    ArtStatus.UNDER_CREATION
            )
        }

        fun replayAll(history: ArtHistory): ArtAggregate {
            val initialState = history.snapshot()
            return history.events()
                    .fold(initialState) { art, event -> art.applyEvent(event) }
        }
    }
}