package com.jarqprog.artapi.applicationservice.commands

import com.jarqprog.artapi.domain.art.ArtGenre
import com.jarqprog.artapi.domain.art.ArtStatus
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.vo.Author
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.domain.vo.User

class CreateArt(
        private val author: Author = Author(),
        private val resource: Resource,
        private val addedBy: User,
        private val artGenre: ArtGenre = ArtGenre.UNDEFINED,
        private val artStatus: ArtStatus = ArtStatus.ACTIVE
) : ArtCommand(Identifier.random(), version = 0) {

    fun author() = author
    fun resource() = resource
    fun addedBy() = addedBy
    fun artGenre() = artGenre
    fun artStatus() = artStatus

    override fun asEvent(): ArtCreated = ArtCreated(
            artId,
            version(),
            timestamp(),
            author(),
            resource(),
            addedBy(),
            artGenre(),
            artStatus()
    )
}