package com.jarqprog.artapi.command.domain


import com.jarqprog.artapi.UNDEFINED
import com.jarqprog.artapi.UNKNOWN
import com.jarqprog.artapi.command.domain.events.ArtCreated
import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.domain.events.ResourceChanged
import com.jarqprog.artapi.command.domain.vo.Author
import com.jarqprog.artapi.command.domain.vo.Resource
import com.jarqprog.artapi.command.domain.vo.User
import java.util.*

class Art(

        private val uuid: UUID,
        private val version: Int,
        private val author: Author,
        private val resource: Resource,
        private val addedBy: User,
        private val genre: ArtGenre,
        private val status: ArtStatus,
        private val history: List<ArtEvent>
) {

    fun uuid() = uuid
    fun version() = version
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
            else ->  throw IllegalArgumentException("invalid unprocessed event: $event")
        }
    }

    private fun apply(event: ArtCreated): Art {
        return Art(
                uuid,
                event.version(),
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
                uuid,
                event.version(),
                author,
                event.resource(),
                addedBy,
                genre,
                status,
                appendChange(event)
        )
    }

    private fun appendChange(event: ArtEvent) = history.plus(event)


    companion object Factory{

        fun initialState(uuid: UUID): Art {
            return Art(
                    uuid,
                    -1,
                    Author(),
                    Resource(UNDEFINED),
                    User(UNKNOWN),
                    ArtGenre.UNDEFINED,
                    ArtStatus.UNDER_CREATION,
                    emptyList()
            )
        }

        fun replayAll(uuid: UUID, events: List<ArtEvent>): Art {
            val initialState = initialState(uuid)
            return events
                    .sortedWith(compareBy(ArtEvent::version))
                    .fold(initialState) { art, event -> art.applyEvent(event) }
        }

        fun replayFromSnapshot(snapshot: Art, events: List<ArtEvent>): Art {
            return events
                    .filter { event -> event.version() > snapshot.version }
                    .sortedWith(compareBy(ArtEvent::version))
                    .toList()
                    .fold(snapshot) { art, event -> art.applyEvent(event) }
        }
    }
}