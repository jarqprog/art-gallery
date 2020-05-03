package com.jarqprog.artapi.command.domain


import com.jarqprog.artapi.EMPTY
import com.jarqprog.artapi.command.domain.events.ArtCreated
import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.domain.events.ResourceUrlChanged
import java.util.*
import kotlin.streams.toList


class Art(

    private val uuid: UUID,
    private val version: Int,
    private val author: String,
    private val resourceUrl: String,
    private val history: List<ArtEvent>
) {

    fun uuid() = uuid
    fun version() = version
    fun author() = author
    fun resourceUrl() = resourceUrl
    fun history() = history

    private fun applyEvent(event: ArtEvent): Art {
        return when (event) {
            is ArtCreated -> apply(event)
            is ResourceUrlChanged -> apply(event)
            else ->  throw IllegalArgumentException("invalid unprocessed event: $event")
        }
    }

    private fun apply(event: ArtCreated): Art {
        return Art(
                uuid,
                event.version(),
                event.author(),
                event.resourceUrl(),
                appendChange(event)
        )
    }

    private fun apply(event: ResourceUrlChanged): Art {
        return Art(
                uuid,
                event.version(),
                author,
                event.newResourceUrl(),
                appendChange(event)
        )
    }

    private fun appendChange(event: ArtEvent) = history.plus(event)

    override fun toString(): String {
        return "Art(uuid=$uuid, version=$version, author='$author', resourceUrl='$resourceUrl', history=$history)"
    }

    companion object Factory{

        fun initialState(uuid: UUID): Art {
            return Art(
                    uuid,
                    version = -1,
                    author = EMPTY,
                    resourceUrl = EMPTY,
                    history = emptyList()
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
                    .toMutableList()
                    .stream()
                    .filter { event -> event.version() > snapshot.version }
                    .sorted(compareBy(ArtEvent::version))
                    .toList()
                    .fold(snapshot) { art, event -> art.applyEvent(event) }
        }
    }
}