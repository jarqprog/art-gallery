package com.jarqprog.artapi.applicationservice.handler.commands

import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.vo.Identifier

abstract class ArtCommand(
        internal val artId: Identifier,
        private val version: Int
) {
    private val name: String = javaClass.simpleName

    fun name() = name
    fun artId() = artId
    fun version() = version
    override fun toString() = "command $name, artId: $artId, version: $version"

    abstract fun asEvent(): ArtEvent
}