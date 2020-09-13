package com.jarqprog.artapi.ports.outgoing.eventstore.entity

import com.jarqprog.artapi.ports.outgoing.MAPPER
import com.jarqprog.artapi.domain.ArtAggregate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll


internal fun assertArtAndSnapshotValuesMatch(art: ArtAggregate, snapshot: Snapshot) {

    assertAll("art and snapshot values should match",
            { assertEquals(art.identifier().value, snapshot.artId) },
            { assertEquals(art.version(), snapshot.version) },
            { assertEquals(art.timestamp(), snapshot.timestamp) },
            { assertEquals(MAPPER.writeValueAsString(art), snapshot.payload) }
    )
}
