package com.jarqprog.artapi.command.domain.events

import java.time.Instant
import java.util.*

class ArtCreated(

        artUuid: UUID,
        version: Int,
        timestamp: Instant,
        private val author: String,
        private val resourceUrl: String

): ArtEvent(artUuid, version, timestamp) {

        fun author(): String = author
        fun resourceUrl(): String = resourceUrl

}


