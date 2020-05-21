package com.jarqprog.artapi.command.ports.outgoing.projection.dto


import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.domain.ArtGenre
import com.jarqprog.artapi.domain.ArtStatus
import com.jarqprog.artapi.domain.vo.Author
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.domain.vo.User
import java.time.Instant

data class ArtDto(
        val identifier: String,
        val version: Int,
        val timestamp: Instant,
        val author: Author,
        val resource: Resource,
        val addedBy: User,
        val genre: ArtGenre,
        val status: ArtStatus
) {

    companion object Factory {

        fun fromArt(art: ArtAggregate) = ArtDto(
                art.identifier().value,
                art.version(),
                art.timestamp(),
                art.author(),
                art.resource(),
                art.addedBy(),
                art.genre(),
                art.status()
        )
    }
}