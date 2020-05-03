package com.jarqprog.artapi.command.domain.events

import java.time.Instant
import java.util.*

abstract class ArtEvent(

        private val artUuid: UUID,
        private val version: Int,
        private val timestamp: Instant

) {
    private val name: String = javaClass.simpleName

    fun name(): String = name
    fun artUuid(): UUID = artUuid
    fun version(): Int = version
    fun timestamp(): Instant = timestamp

    override fun toString(): String {
        return "ArtEvent(artUuid=$artUuid, version=$version, timestamp=$timestamp, name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArtEvent) return false

        if (artUuid != other.artUuid) return false
        if (version != other.version) return false
        if (timestamp != other.timestamp) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = artUuid.hashCode()
        result = 31 * result + version
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}