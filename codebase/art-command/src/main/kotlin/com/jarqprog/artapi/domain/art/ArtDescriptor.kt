package com.jarqprog.artapi.domain.art

import java.time.Instant
import java.util.*

data class ArtDescriptor(
        val id: UUID,
        val artId: UUID,
        val version: Int,
        val timestamp: Instant,
        val payload: String
) {
    fun isNotLaterThan(stateAt: Instant) = timestamp <= stateAt
}