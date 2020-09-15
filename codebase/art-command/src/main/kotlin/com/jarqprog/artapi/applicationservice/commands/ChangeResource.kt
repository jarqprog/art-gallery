package com.jarqprog.artapi.applicationservice.commands

import com.jarqprog.artapi.domain.events.ResourceChanged
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource

class ChangeResource(
        artId: Identifier,
        version: Int,
        private val resource: Resource
) : ArtCommand(artId, version) {

    fun resource() = resource

    override fun asEvent(): ResourceChanged = ResourceChanged(
            artId,
            version().plus(1),
            timestamp(),
            resource()
    )

}