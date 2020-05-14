package com.jarqprog.artapi.command.domain.events

import com.jarqprog.artapi.command.domain.vo.Identifier
import java.time.Instant


abstract class ArtEvent(

        private val artId: Identifier,
        private val version: Int,
        private val timestamp: Instant

) {
    private val type: String = javaClass.superclass.simpleName
    private val name: String = javaClass.simpleName

    fun type() = type
    fun name() = name
    fun artId() = artId
    fun version() = version
    fun timestamp() = timestamp
    override fun toString() = "event $name, artId: $artId, version: $version, timestamp: $timestamp"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArtEvent) return false

        if (artId != other.artId) return false
        if (version != other.version) return false
        if (timestamp != other.timestamp) return false
        if (type != other.type) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = artId.hashCode()
        result = 31 * result + version
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}