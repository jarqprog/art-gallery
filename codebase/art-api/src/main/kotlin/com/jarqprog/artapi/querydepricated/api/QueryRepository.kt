package com.jarqprog.artapi.querydepricated.api

import java.util.*

interface QueryRepository {

    fun findByUuid(uuid: UUID): Optional<String>

}