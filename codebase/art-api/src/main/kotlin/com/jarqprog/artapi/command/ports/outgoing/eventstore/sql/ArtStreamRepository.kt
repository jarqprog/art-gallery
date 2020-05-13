package com.jarqprog.artapi.command.ports.outgoing.eventstore.sql

import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ArtHistoryDescriptor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ArtStreamRepository : CrudRepository<ArtHistoryDescriptor, String> {

//    @Query("select version from ART_EVENT_STREAM a where a.artId = :artId")
//    fun findVersionByArtId(@Param("artId") artId: String): Optional<Int>
    fun findVersionByArtId(artId: String): Optional<Int>

}