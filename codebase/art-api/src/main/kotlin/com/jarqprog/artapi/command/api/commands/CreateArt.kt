package com.jarqprog.artapi.command.api.commands

import com.jarqprog.artapi.domain.ArtGenre
import com.jarqprog.artapi.domain.ArtStatus
import com.jarqprog.artapi.domain.vo.Author
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.domain.vo.User

class CreateArt(

        artId: Identifier = Identifier.random(),
        private val author: Author = Author(),
        private val resource: Resource,
        private val addedBy: User,
        private val artGenre: ArtGenre = ArtGenre.UNDEFINED,
        private val artStatus: ArtStatus = ArtStatus.ACTIVE

) : ArtCommand(artId, version = 0) {

    fun author() = author
    fun resource() = resource
    fun addedBy() = addedBy
    fun artGenre() = artGenre
    fun artStatus() = artStatus
}