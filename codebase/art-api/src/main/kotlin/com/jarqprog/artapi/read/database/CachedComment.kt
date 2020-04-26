package com.jarqprog.artapi.read.database

import com.jarqprog.artapi.write.dto.CommentDTO
import java.time.LocalDateTime
import java.util.*

data class CachedComment(val comment: Optional<CommentDTO>, private val localDateTime: LocalDateTime = LocalDateTime.now()) {

    fun isOlderThan(minutesAgo: Long): Boolean = localDateTime.isBefore(LocalDateTime.now().minusMinutes(minutesAgo))
    fun isNotOlderThan(minutesAgo: Long): Boolean = localDateTime.isAfter(LocalDateTime.now().minusMinutes(minutesAgo))
}

