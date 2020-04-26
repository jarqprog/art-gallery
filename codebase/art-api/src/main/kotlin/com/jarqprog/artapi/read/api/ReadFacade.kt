package com.jarqprog.artapi.read.api

import java.util.UUID
import java.util.Optional

interface ReadFacade {

    fun load(uuid: UUID): Optional<String>

}