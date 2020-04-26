package com.jarqprog.artapi.write.domain

import java.time.LocalDateTime
import java.util.*

interface Comment {

    fun uuid(): UUID
    fun date(): LocalDateTime
    fun archived(): Boolean
    fun author(): String
    fun content(): String
}