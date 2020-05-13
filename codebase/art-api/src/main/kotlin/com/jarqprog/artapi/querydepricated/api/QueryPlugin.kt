package com.jarqprog.artapi.querydepricated.api

import java.util.*


class QueryPlugin(private val repository: QueryRepository) : QueryFacade {

    override fun load(uuid: UUID): Optional<String> = repository.findByUuid(uuid)

}