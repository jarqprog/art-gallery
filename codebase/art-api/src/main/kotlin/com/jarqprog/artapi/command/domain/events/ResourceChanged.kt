package com.jarqprog.artapi.command.domain.events

import com.jarqprog.artapi.command.domain.vo.Resource
import java.time.Instant
import java.util.*

class ResourceChanged(

        artUuid: UUID,
        version: Int,
        timestamp: Instant,
        private val resource: Resource

): ArtEvent(artUuid, version, timestamp) {

    fun resource() = resource
}