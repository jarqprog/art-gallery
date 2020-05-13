package com.jarqprog.artapi.writedepricated.domain

import java.time.LocalDateTime
import java.util.*

interface Art {

    fun uuid(): UUID
    fun date(): LocalDateTime
    fun archived(): Boolean
    fun author(): String
    fun path(): String
    fun comments(): Set<Comment>

}