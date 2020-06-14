package com.jarqprog.artapi.domain.events

import com.jarqprog.artapi.domain.ArtGenre
import com.jarqprog.artapi.domain.ArtStatus
import com.jarqprog.artapi.domain.commands.CreateArt
import com.jarqprog.artapi.domain.vo.Author
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.domain.vo.User
import java.time.Instant

class ArtCreated private constructor(
        artId: Identifier,
        version: Int,
        timestamp: Instant,
        private val author: Author,
        private val resource: Resource,
        private val addedBy: User,
        private val artGenre: ArtGenre,
        private val artStatus: ArtStatus) : ArtEvent(artId, version, timestamp) {

    fun author() = author
    fun resource() = resource
    fun addedBy() = addedBy
    fun artGenre() = artGenre
    fun artStatus() = artStatus

    companion object Factory {
        fun from(command: CreateArt): ArtCreated {
            return ArtCreated(
                    command.artId,
                    command.version(),
                    Instant.now(),
                    command.author(),
                    command.resource(),
                    command.addedBy(),
                    command.artGenre(),
                    command.artStatus()
            )
        }
    }
}


