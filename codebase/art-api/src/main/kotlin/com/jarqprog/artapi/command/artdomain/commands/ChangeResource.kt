package com.jarqprog.artapi.command.artdomain.commands

import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.artdomain.vo.Resource

class ChangeResource(

        artId: Identifier,
        version: Int,
        private val resource: Resource

) : ArtCommand(artId, version) {

    fun resource() = resource

}