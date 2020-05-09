package com.jarqprog.artapi.command.infrastructure.eventstore

import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.EventStream
import java.util.*

interface Database {

    fun existsById(artId: Identifier): Boolean
    fun streamVersion(artEvent: ArtEvent): Optional<Int>
    fun save(eventStream: EventStream)
    fun load(artId: Identifier): Optional<EventStream>

}