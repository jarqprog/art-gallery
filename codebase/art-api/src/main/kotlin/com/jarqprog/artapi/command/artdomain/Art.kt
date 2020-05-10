package com.jarqprog.artapi.command.artdomain


import com.jarqprog.artapi.UNDEFINED
import com.jarqprog.artapi.UNKNOWN
import com.jarqprog.artapi.command.artdomain.events.ArtCreated
import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.events.ResourceChanged
import com.jarqprog.artapi.command.artdomain.exceptions.EventProcessingFailure
import com.jarqprog.artapi.command.artdomain.vo.Author
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.artdomain.vo.Resource
import com.jarqprog.artapi.command.artdomain.vo.User
import java.time.Instant

class Art(

        private val identifier: Identifier,
        private val version: Int,
        private val timestamp: Instant,
        private val author: Author,
        private val resource: Resource,
        private val addedBy: User,
        private val genre: ArtGenre,
        private val status: ArtStatus,
        private val history: List<ArtEvent>
) {

    fun identifier() = identifier
    fun version() = version
    fun timestamp() = timestamp
    fun author() = author
    fun resource() = resource
    fun addedBy() = addedBy
    fun genre() = genre
    fun status() = status
    fun history() = history

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

    private fun appendChange(event: ArtEvent) = history.plus(event)


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

        fun replayAll(identifier: Identifier, events: List<ArtEvent>): Art {
            val initialState = initialState(identifier)
            return events
                    .sortedWith(compareBy(ArtEvent::timestamp))
                    .fold(initialState) { art, event -> art.applyEvent(event) }
        }

        fun replayFromSnapshot(snapshot: Art, events: List<ArtEvent>): Art {
            return events
                    .filter { event -> event.timestamp() > snapshot.timestamp }
                    .sortedWith(compareBy(ArtEvent::timestamp))
                    .toList()
                    .fold(snapshot) { art, event -> art.applyEvent(event) }
        }
    }
}