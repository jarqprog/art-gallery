package com.jarqprog.artapi.command.domain.commands

import java.util.*

class ChangeResourceUrl(

        artUuid: UUID,
        version: Int,
        private val newResourceUrl: String

): ArtCommand(artUuid, version) {

    fun newResourceUrl(): String = newResourceUrl

}