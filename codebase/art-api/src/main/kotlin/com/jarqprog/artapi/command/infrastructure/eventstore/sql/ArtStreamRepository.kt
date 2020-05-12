package com.jarqprog.artapi.command.infrastructure.eventstore.sql

import com.jarqprog.artapi.command.infrastructure.eventstore.entity.ArtHistoryDescriptor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtStreamRepository : CrudRepository<ArtHistoryDescriptor,String> {
}