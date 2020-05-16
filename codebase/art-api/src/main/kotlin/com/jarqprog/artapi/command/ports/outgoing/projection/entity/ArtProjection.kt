package com.jarqprog.artapi.command.ports.outgoing.projection.entity

import com.jarqprog.artapi.command.ports.outgoing.projection.dto.ArtDto
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "ART_PROJECTION")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class ArtProjection(

        @Id
        val identifier: String,
        val version: Int,
        val timestamp: Instant,

        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb", name = "ART")
        val art: ArtDto
) {
    companion object Factory {

        fun fromDto(artDto: ArtDto) = ArtProjection(
                artDto.identifier,
                artDto.version,
                artDto.timestamp,
                artDto
        )
    }
}