package com.jarqprog.artapi.command.domain.commands

import com.jarqprog.artapi.command.domain.vo.Identifier
import com.jarqprog.artapi.command.domain.vo.Resource

class ChangeResource(

        artId: Identifier,
        version: Int,
        private val resource: Resource

) : ArtCommand(artId, version) {

    fun resource() = resource

}