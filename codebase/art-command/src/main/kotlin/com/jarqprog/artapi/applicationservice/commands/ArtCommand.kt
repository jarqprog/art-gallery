package com.jarqprog.artapi.applicationservice.commands

import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.vo.Identifier
import java.time.Instant
import java.time.Instant.now

abstract class ArtCommand(
        internal val artId: Identifier,
        private val version: Int,
        private val timestamp: Instant = now()
) {
    private val name: String = javaClass.simpleName

    fun name() = name
    fun artId() = artId
    fun version() = version
    fun timestamp() = timestamp
    override fun toString() = "command $name, artId: $artId, version: $version, timestamp: $timestamp"

    abstract fun asEvent(): ArtEvent
}