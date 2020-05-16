package com.jarqprog.artapi.command.ports.outgoing.eventstore.entity


import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.Instant
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id


@Entity(name = "ART_EVENT")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class ArtEventDescriptor(

        @Id
        val uuid: UUID,
        @Column(updatable = false)
        val artId: String,
        @Column(updatable = false)
        val version: Int,
        @Column(updatable = false)
        val timestamp: Instant,
        @Column(updatable = false)
        val type: String,
        @Column(updatable = false)
        val name: String,

        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb", updatable = false)
        val payload: String
) {

    override fun toString() = "event $name, artId: $artId, version: $version, timestamp: $timestamp"

    fun isNotLaterThan(stateAt: Instant) = timestamp <= stateAt

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArtEventDescriptor) return false

        if (uuid != other.uuid) return false
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