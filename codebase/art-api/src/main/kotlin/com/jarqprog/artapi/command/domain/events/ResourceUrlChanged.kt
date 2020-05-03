package com.jarqprog.artapi.command.domain.events

import java.time.Instant
import java.util.*

class ResourceUrlChanged(

        artUuid: UUID,
        version: Int,
        timestamp: Instant,
        private val newResourceUrl: String

): ArtEvent(artUuid, version, timestamp) {

    fun newResourceUrl(): String = newResourceUrl
}