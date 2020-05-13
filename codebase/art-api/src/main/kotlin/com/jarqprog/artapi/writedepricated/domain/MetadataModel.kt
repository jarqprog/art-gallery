package com.jarqprog.artapi.writedepricated.domain

import com.jarqprog.artapi.EMPTY
import com.jarqprog.artapi.FAKE_CLIENT
import java.time.Instant
import java.util.*

class MetadataModel(

        private val uuid: UUID = UUID.randomUUID(),
        private val dataUUID: UUID,
        private val created: Instant = Instant.now(),
        private val info: String = EMPTY,
        private val clientUUID: UUID = FAKE_CLIENT

) : Metadata {

    override fun uuid(): UUID = uuid

    override fun dataUUID(): UUID = dataUUID

    override fun created(): Instant = created

    override fun info(): String = info

    override fun clientUUID(): UUID = clientUUID

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MetadataModel) return false
        return dataUUID == other.dataUUID
    }

    override fun hashCode(): Int {
        return dataUUID.hashCode()
    }
}