package com.jarqprog.artapi.ports.outgoing.eventstore.entity

import java.time.Instant
import java.util.*

data class EventDescriptor(
        val id: UUID,
        val artId: UUID,
        val version: Int,
        val timestamp: Instant,
        val type: String,
        val name: String,
        val payload: String
) {

    override fun toString() = "event $name, artId: $artId, version: $version, timestamp: $timestamp"

    fun isNotLaterThan(stateAt: Instant) = timestamp <= stateAt

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EventDescriptor) return false

        if (id != other.id) return false
        if (artId != other.artId) return false
        if (version != other.version) return false
        if (timestamp != other.timestamp) return false
        if (type != other.type) return false
        if (name != other.name) return false
        if (payload != other.payload) return false
        return true
    }

    override fun hashCode(): Int {
        var result = artId.hashCode()
        result = 31 * result + version
        result = 31 * result + type.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}