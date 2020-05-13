package com.jarqprog.artapi.command.ports.outgoing.eventstore.sql

import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ArtHistoryDescriptor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtStreamRepository : CrudRepository<ArtHistoryDescriptor,String> {
}