package com.jarqprog.artapi.command.ports.outgoing

import com.jarqprog.artapi.command.domain.ArtHistory
import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.exceptions.EventStoreFailure
import java.time.Instant
import java.util.*

interface EventStore {

    fun save(event: ArtEvent): Optional<EventStoreFailure>
    fun load(artId: Identifier): Optional<ArtHistory>
    fun load(artId: Identifier, stateAt: Instant): Optional<ArtHistory>

}