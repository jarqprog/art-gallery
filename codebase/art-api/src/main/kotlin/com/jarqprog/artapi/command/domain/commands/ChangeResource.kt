package com.jarqprog.artapi.command.domain.commands

import com.jarqprog.artapi.command.domain.vo.Resource
import java.util.*

class ChangeResource(

        artUuid: UUID,
        version: Int,
        private val resource: Resource

): ArtCommand(artUuid, version) {

    fun resource() = resource

}