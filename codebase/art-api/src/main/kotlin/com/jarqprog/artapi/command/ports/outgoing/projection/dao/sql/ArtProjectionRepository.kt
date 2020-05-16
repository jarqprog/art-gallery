package com.jarqprog.artapi.command.ports.outgoing.projection.dao.sql

import com.jarqprog.artapi.command.ports.outgoing.projection.entity.ArtProjection
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtProjectionRepository : CrudRepository<ArtProjection, String>