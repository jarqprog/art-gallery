package com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.sql

import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ArtHistoryDescriptor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ArtStreamRepository : CrudRepository<ArtHistoryDescriptor, String> {

    fun findVersionByArtId(artId: String): Optional<Int>

}