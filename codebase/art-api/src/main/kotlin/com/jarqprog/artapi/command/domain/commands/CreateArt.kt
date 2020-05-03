package com.jarqprog.artapi.command.domain.commands

import com.jarqprog.artapi.command.domain.ArtGenre
import com.jarqprog.artapi.command.domain.ArtStatus
import com.jarqprog.artapi.command.domain.vo.Author
import com.jarqprog.artapi.command.domain.vo.Resource
import com.jarqprog.artapi.command.domain.vo.User
import java.util.*

class CreateArt(

        artUuid: UUID = UUID.randomUUID(),
        private val author: Author = Author(),
        private val resource: Resource,
        private val addedBy: User,
        private val artGenre: ArtGenre = ArtGenre.UNDEFINED,
        private val artStatus: ArtStatus = ArtStatus.ACTIVE

        ): ArtCommand(artUuid, version = 0) {

    fun author() = author
    fun resource() = resource
    fun addedBy() = addedBy
    fun artGenre() = artGenre
    fun artStatus() = artStatus
}