package com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.inmemory

import com.jarqprog.artapi.command.ports.outgoing.eventstore.SnapshotDatabase
import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.Snapshot
import com.jarqprog.artapi.domain.vo.Identifier
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemorySnapshotDatabase(private val memory: ConcurrentHashMap<String, MutableList<Snapshot>>) : SnapshotDatabase {

    override fun save(snapshot: Snapshot) {
        Optional.ofNullable(memory[snapshot.artId])
                .ifPresentOrElse(
                        { snapshots -> snapshots.add(snapshot) },
                        { memory[snapshot.artId] = mutableListOf(snapshot) }
                )
    }

    override fun loadLatest(artId: Identifier): Optional<Snapshot> {
        return Optional.ofNullable(memory[artId.value])
                .map { snapshots -> snapshots
                            .sortedWith(Comparator.comparing(Snapshot::timestamp))
                            .last()
                }
    }
}
