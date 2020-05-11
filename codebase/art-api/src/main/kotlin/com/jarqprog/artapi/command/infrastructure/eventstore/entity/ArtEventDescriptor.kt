package com.jarqprog.artapi.command.infrastructure.eventstore.entity

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
        val artId: String,
        val version: Int,
        val timestamp: Instant,
        val eventType: String,
        val eventName: String,

        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb")
        val payload: String
) {

    fun isNotLaterThan(stateAt: Instant) = timestamp <= stateAt

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArtEventDescriptor) return false

        if (uuid != other.uuid) return false
        if (artId != other.artId) return false
        if (version != other.version) return false
        if (timestamp != other.timestamp) return false
        if (eventType != other.eventType) return false
        if (eventName != other.eventName) return false
        if (payload != other.payload) return false
        return true
    }

    override fun hashCode(): Int {
        var result = artId.hashCode()
        result = 31 * result + version
        result = 31 * result + eventType.hashCode()
        result = 31 * result + eventName.hashCode()
        return result
    }
}