package com.jarqprog.artapi.command.infrastructure.eventstore

import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.ArtHistoryDescriptor
import java.util.*

interface EventStreamDatabase {

    fun historyExistsById(artId: Identifier): Boolean
    fun streamVersion(artEvent: ArtEvent): Optional<Int>
    fun save(eventStream: ArtHistoryDescriptor)
    fun load(artId: Identifier): Optional<ArtHistoryDescriptor>

}