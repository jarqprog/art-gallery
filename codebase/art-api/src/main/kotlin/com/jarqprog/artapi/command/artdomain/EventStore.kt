package com.jarqprog.artapi.command.artdomain

import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.infrastructure.eventstore.exceptions.EventStoreFailure
import java.time.Instant
import java.util.*

interface EventStore {

    fun save(event: ArtEvent): Optional<EventStoreFailure>
    fun load(artId: Identifier): Optional<ArtHistory>
    fun load(artId: Identifier, stateAt: Instant): Optional<ArtHistory>

}