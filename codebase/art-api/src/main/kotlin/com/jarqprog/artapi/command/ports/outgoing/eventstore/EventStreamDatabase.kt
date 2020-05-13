package com.jarqprog.artapi.command.ports.outgoing.eventstore

import com.jarqprog.artapi.command.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.ArtHistoryDescriptor
import java.util.*

interface EventStreamDatabase {

    fun historyExistsById(artId: Identifier): Boolean
    fun streamVersion(artId: Identifier): Optional<Int>
    fun save(eventStream: ArtHistoryDescriptor)
    fun load(artId: Identifier): Optional<ArtHistoryDescriptor>

}