package com.jarqprog.artapi.command.ports.outgoing.eventstore.dao.sql

import com.jarqprog.artapi.command.ports.outgoing.eventstore.entity.Snapshot
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SnapshotRepository : CrudRepository<Snapshot, String> {
}