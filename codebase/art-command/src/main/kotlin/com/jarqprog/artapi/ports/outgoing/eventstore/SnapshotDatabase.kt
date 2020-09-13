package com.jarqprog.artapi.ports.outgoing.eventstore

import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.ports.outgoing.eventstore.entity.Snapshot
import java.util.*

interface SnapshotDatabase {

    fun save(snapshot: Snapshot)
    fun loadLatest(artId: Identifier): Optional<Snapshot>

}