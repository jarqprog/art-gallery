package com.jarqprog.artapi.query.api

import java.util.UUID
import java.util.Optional

interface QueryFacade {

    fun load(uuid: UUID): Optional<String>

}