package com.jarqprog.artapi.write.database

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity(@Id private val uuid: UUID,
                          private val date: LocalDateTime,
                          private val archived: Boolean = false) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseEntity) return false
        return uuid == other.uuid
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }

    fun uuid() = uuid
    fun date() = date
    fun archived() = archived
}