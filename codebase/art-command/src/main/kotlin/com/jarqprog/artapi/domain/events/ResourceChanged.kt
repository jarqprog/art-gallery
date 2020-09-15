package com.jarqprog.artapi.domain.events

import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource
import java.time.Instant

class ResourceChanged (
        artId: Identifier,
        version: Int,
        timestamp: Instant,
        private val resource: Resource) : ArtEvent(artId, version, timestamp) {

    fun resource() = resource
}