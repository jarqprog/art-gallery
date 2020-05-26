package com.jarqprog.artapi.command.ports.outgoing.projection.dao.sql

import com.jarqprog.artapi.command.ports.outgoing.projection.ProjectionDatabase
import com.jarqprog.artapi.command.ports.outgoing.projection.entity.ArtProjection

class ProjectionJDBC(private val artProjectionRepository: ArtProjectionRepository) : ProjectionDatabase {

    override fun save(artProjection: ArtProjection) {
        artProjectionRepository.save(artProjection)
    }
}