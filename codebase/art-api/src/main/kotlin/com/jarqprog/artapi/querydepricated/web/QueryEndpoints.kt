package com.jarqprog.artapi.querydepricated.web

import com.jarqprog.artapi.querydepricated.api.QueryFacade
import com.jarqprog.artapi.querydepricated.exceptions.NotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/art")
class QueryEndpoints(@Autowired private val facade: QueryFacade) {

    @GetMapping("/{uuid}")
    fun find(@PathVariable("uuid") uuid: UUID): String {
        return facade.load(uuid)
                .orElseThrow { NotFound(uuid) }
    }
}
