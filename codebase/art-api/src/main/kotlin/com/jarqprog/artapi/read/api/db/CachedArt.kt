package com.jarqprog.artapi.read.api.db

import java.time.LocalDateTime
import java.util.*

data class CachedArt(val art: Optional<String>, private val localDateTime: LocalDateTime = LocalDateTime.now()) {

    fun isOlderThan(minutesAgo: Long): Boolean = localDateTime.isBefore(LocalDateTime.now().minusMinutes(minutesAgo))
    fun isNotOlderThan(minutesAgo: Long): Boolean = localDateTime.isAfter(LocalDateTime.now().minusMinutes(minutesAgo))
}

