package com.jarqprog.artapi.command.domain.events

import com.jarqprog.artapi.command.domain.vo.Identifier
import com.jarqprog.artapi.command.domain.vo.Resource
import java.time.Instant

class ResourceChanged(

        artId: Identifier,
        version: Int,
        timestamp: Instant,
        private val resource: Resource

) : ArtEvent(artId, version, timestamp) {

    fun resource() = resource
}