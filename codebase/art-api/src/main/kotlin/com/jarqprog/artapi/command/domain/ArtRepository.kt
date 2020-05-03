package com.jarqprog.artapi.command.domain

import java.time.Instant
import java.util.*


interface ArtRepository {

    fun save(aggregate: Art): Art

    fun getByUUID(uuid: UUID): Art

    fun getByUuidAt(uuid: UUID, at: Instant): Art
}