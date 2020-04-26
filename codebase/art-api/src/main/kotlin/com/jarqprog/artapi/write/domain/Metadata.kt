package com.jarqprog.artapi.write.domain

import java.time.Instant
import java.util.*

interface Metadata {

    fun uuid(): UUID
    fun dataUUID(): UUID
    fun created(): Instant
    fun info(): String
    fun clientUUID(): UUID

}