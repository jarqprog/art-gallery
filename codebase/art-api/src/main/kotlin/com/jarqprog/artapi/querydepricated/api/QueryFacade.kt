package com.jarqprog.artapi.querydepricated.api

import java.util.UUID
import java.util.Optional

interface QueryFacade {

    fun load(uuid: UUID): Optional<String>

}