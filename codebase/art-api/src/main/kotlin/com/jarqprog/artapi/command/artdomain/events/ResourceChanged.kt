package com.jarqprog.artapi.command.artdomain.events

import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.artdomain.vo.Resource
import java.time.Instant

class ResourceChanged(

        artId: Identifier,
        version: Int,
        timestamp: Instant,
        private val resource: Resource

) : ArtEvent(artId, version, timestamp) {

    fun resource() = resource
}