package com.jarqprog.artapi.query.api

import java.util.*

interface QueryRepository {

    fun findByUuid(uuid: UUID): Optional<String>

}