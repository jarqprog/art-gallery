package com.jarqprog.artapi.write.domain

import java.time.LocalDateTime
import java.util.*

abstract class BaseModel (
        private val uuid: UUID,
        private val date: LocalDateTime,
        private val archived: Boolean,
        private val metadata: MetadataModel
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseModel) return false
        return uuid == other.uuid
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }

    fun uuid() = uuid
    fun date() = date
    fun archived() = archived
    fun metadata(): MetadataModel = metadata

    override fun toString(): String {
        return "BaseModel(uuid=$uuid, date=$date, archived=$archived, meta=$metadata)"
    }
}