package com.jarqprog.artapi.command.ports.outgoing.eventstore.entity


import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.Instant
import java.util.*
import javax.persistence.*


@Entity(name = "ART_SNAPSHOT")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class Snapshot(

        @Id
        val uuid: UUID,
        @Column(updatable = false)
        val artId: String,
        @Column(updatable = false)
        val version: Int,
        @Column(updatable = false)
        val timestamp: Instant,

        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb", updatable = false)
        val payload: String
) {

    fun isNotLaterThan(stateAt: Instant) = timestamp <= stateAt

}