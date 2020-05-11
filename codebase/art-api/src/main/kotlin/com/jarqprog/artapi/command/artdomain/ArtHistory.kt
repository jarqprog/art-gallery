package com.jarqprog.artapi.command.artdomain

import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import java.time.Instant

data class ArtHistory(
        val artId: Identifier,
        val version: Int,
        val timestamp: Instant,
        val events: List<ArtEvent>
) {

    companion object Factory{

        fun initialize(artId: Identifier): ArtHistory = ArtHistory(artId, 0, Instant.now(), emptyList())

    }

}