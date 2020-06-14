package com.jarqprog.artapi.domain.commands

import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource

class ChangeResource(
        artId: Identifier,
        version: Int,
        private val resource: Resource
) : ArtCommand(artId, version) {

    fun resource() = resource

}