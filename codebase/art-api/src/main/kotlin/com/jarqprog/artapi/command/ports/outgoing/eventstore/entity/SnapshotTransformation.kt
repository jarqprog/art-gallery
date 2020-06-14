package com.jarqprog.artapi.command.ports.outgoing.eventstore.entity

import com.jarqprog.artapi.command.ports.outgoing.MAPPER
import com.jarqprog.artapi.domain.ArtAggregate
import java.util.*
import java.util.function.Function

class SnapshotCreation : Function<ArtAggregate, Snapshot> {

    override fun apply(art: ArtAggregate): Snapshot {
        return Snapshot(
                UUID.randomUUID(),
                art.identifier().value,
                art.version(),
                art.timestamp(),
                MAPPER.writeValueAsString(art)
        )
    }
}

class SnapshotToArt : Function<Snapshot, ArtAggregate> {

    override fun apply(snapshot: Snapshot): ArtAggregate {
        return MAPPER.readValue(snapshot.payload, ArtAggregate::class.java)
    }
}
