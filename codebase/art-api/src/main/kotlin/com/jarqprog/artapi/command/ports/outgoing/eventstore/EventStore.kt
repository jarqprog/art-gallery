package com.jarqprog.artapi.command.ports.outgoing.eventstore

import arrow.core.Either
import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.command.ports.outgoing.eventstore.exceptions.EventStoreFailure
import java.time.Instant
import java.util.*

interface EventStore {

    fun save(event: ArtEvent): Optional<EventStoreFailure>
    fun load(artId: Identifier): Either<EventStoreFailure, Optional<ArtHistory>>
    fun load(artId: Identifier, stateAt: Instant): Either<EventStoreFailure, Optional<ArtHistory>>

}