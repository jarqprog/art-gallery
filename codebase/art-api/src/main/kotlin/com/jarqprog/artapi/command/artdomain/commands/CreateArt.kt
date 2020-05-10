package com.jarqprog.artapi.command.artdomain.commands

import com.jarqprog.artapi.command.artdomain.ArtGenre
import com.jarqprog.artapi.command.artdomain.ArtStatus
import com.jarqprog.artapi.command.artdomain.vo.Author
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.artdomain.vo.Resource
import com.jarqprog.artapi.command.artdomain.vo.User
import java.util.*

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