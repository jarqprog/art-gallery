package com.jarqprog.artapi.ports.outgoing.projection

import com.jarqprog.artapi.ports.outgoing.projection.entity.ArtProjection

interface ProjectionDatabase {

    fun save(artProjection: ArtProjection)
}