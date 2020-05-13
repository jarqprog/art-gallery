package com.jarqprog.artapi.command.domain


import com.jarqprog.artapi.UNDEFINED
import com.jarqprog.artapi.UNKNOWN
import com.jarqprog.artapi.command.domain.events.ArtCreated
import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.domain.events.ResourceChanged
import com.jarqprog.artapi.command.api.exceptions.EventProcessingFailure
import com.jarqprog.artapi.command.domain.vo.Author
import com.jarqprog.artapi.command.domain.vo.Identifier
import com.jarqprog.artapi.command.domain.vo.Resource
import com.jarqprog.artapi.command.domain.vo.User
import java.time.Instant

class Art private constructor(
        private val identifier: Identifier,
        private val version: Int,
        private val timestamp: Instant,
        private val author: Author,
        private val resource: Resource,
        private val addedBy: User,
        private val genre: ArtGenre,
        private val status: ArtStatus,
        private val events: List<ArtEvent>
) {
    fun identifier() = identifier
    fun version() = version
    fun timestamp() = timestamp
    fun author() = author
    fun resource() = resource
    fun addedBy() = addedBy
    fun genre() = genre
    fun status() = status

    private fun applyEvent(event: ArtEvent): Art {
        return when (event) {
            is ArtCreated -> apply(event)
            is ResourceChanged -> apply(event)
            else -> throw EventProcessingFailure("invalid unprocessed event: $event")
        }
    }

    private fun apply(event: ArtCreated): Art {
        return Art(
                identifier,
                event.version(),
                event.timestamp(),
                event.author(),
                event.resource(),
                event.addedBy(),
                event.artGenre(),
                event.artStatus(),
                appendChange(event)
        )
    }

    private fun apply(event: ResourceChanged): Art {
        return Art(
                identifier,
                event.version(),
                event.timestamp(),
                author,
                event.resource(),
                addedBy,
                genre,
                status,
                appendChange(event)
        )
    }

    private fun appendChange(event: ArtEvent) = events.plus(event)

    companion object Factory {

        fun initialState(identifier: Identifier): Art {
            return Art(
                    identifier,
                    -1,
                    Instant.now(),
                    Author(),
                    Resource(UNDEFINED),
                    User(UNKNOWN),
                    ArtGenre.UNDEFINED,
                    ArtStatus.UNDER_CREATION,
                    emptyList()
            )
        }

        fun replayAll(identifier: Identifier, history: ArtHistory): Art {
            val initialState = initialState(identifier)
            return history.events()
                    .fold(initialState) { art, event -> art.applyEvent(event) }
        }

        fun replayFromSnapshot(snapshot: Art, history: ArtHistory): Art {
            return history.events()
                    .filter { event -> event.timestamp() > snapshot.timestamp }
                    .toList()
                    .fold(snapshot) { art, event -> art.applyEvent(event) }
        }
    }
}