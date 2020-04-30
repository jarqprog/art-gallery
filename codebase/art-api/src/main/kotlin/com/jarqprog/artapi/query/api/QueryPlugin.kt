package com.jarqprog.artapi.query.api

import java.util.*


class QueryPlugin(private val repository: QueryRepository): QueryFacade {

    override fun load(uuid: UUID): Optional<String> = repository.findByUuid(uuid)

}