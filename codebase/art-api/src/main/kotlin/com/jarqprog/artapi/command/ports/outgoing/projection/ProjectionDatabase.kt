package com.jarqprog.artapi.command.ports.outgoing.projection

import com.jarqprog.artapi.command.ports.outgoing.projection.entity.ArtProjection

interface ProjectionDatabase {

    fun save(artProjection: ArtProjection)
}