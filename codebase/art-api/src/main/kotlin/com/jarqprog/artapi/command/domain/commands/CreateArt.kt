package com.jarqprog.artapi.command.domain.commands

import com.jarqprog.artapi.UNKNOWN
import com.jarqprog.artapi.UNDEFINED
import java.util.*

class CreateArt(

        newArtUuid: UUID = UUID.randomUUID(),
        private val author: String = UNKNOWN,
        private val resourceUrl: String

): ArtCommand(newArtUuid, version = 0) {

    fun author(): String = author
    fun resourceUrl(): String = resourceUrl

}