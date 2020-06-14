package com.jarqprog.artapi.command.ports.outgoing.eventstore.entity

import java.time.Instant
import java.util.*


data class Snapshot(

        val uuid: UUID,
        val artId: String,
        val version: Int,
        val timestamp: Instant,
        val payload: String
) {
    fun isNotLaterThan(stateAt: Instant) = timestamp <= stateAt
}