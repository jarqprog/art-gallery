package com.jarqprog.artapi.domain.events

import com.jarqprog.artapi.domain.commands.ChangeResource
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource
import java.time.Instant

class ResourceChanged private constructor(
        artId: Identifier,
        version: Int,
        timestamp: Instant,
        private val resource: Resource) : ArtEvent(artId, version, timestamp) {

    fun resource() = resource

    companion object Factory {
        fun from(command: ChangeResource): ResourceChanged {
            return ResourceChanged(
                    command.artId,
                    command.version().plus(1),
                    Instant.now(),
                    command.resource()
            )
        }
    }
}