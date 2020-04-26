package com.jarqprog.artapi.write.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.*

abstract class BaseDTO (private val uuid: UUID = UUID.randomUUID(),
                        private val date: LocalDateTime = LocalDateTime.now(),
                        private val archived: Boolean = false) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseDTO) return false
        return uuid == other.uuid
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }

    @JsonProperty
    fun uuid() = uuid

    @JsonProperty
    fun date() = date

    @JsonProperty
    fun archived() = archived
}