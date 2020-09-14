package com.jarqprog.artapi.applicationservice.commands

import com.jarqprog.artapi.domain.events.ResourceChanged
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource
import java.time.Instant.now

class ChangeResource(
        artId: Identifier,
        version: Int,
        private val resource: Resource
) : ArtCommand(artId, version) {

    fun resource() = resource

    override fun asEvent(): ResourceChanged = ResourceChanged(
            artId,
            version().plus(1),
            now(),
            resource()
    )

}