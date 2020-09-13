package com.jarqprog.artapi.ports.outgoing.projection.entity

import com.jarqprog.artapi.ports.outgoing.projection.dto.ArtDto
import java.time.Instant


data class ArtProjection(

        val identifier: String,
        val version: Int,
        val timestamp: Instant,
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