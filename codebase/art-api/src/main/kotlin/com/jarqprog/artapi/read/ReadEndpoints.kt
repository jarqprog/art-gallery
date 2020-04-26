package com.jarqprog.artapi.read

import com.jarqprog.artapi.read.api.ReadFacade
import com.jarqprog.artapi.read.exceptions.NotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/art")
class ReadEndpoints(@Autowired private val facade: ReadFacade) {

    @GetMapping("/{uuid}")
    fun find(@PathVariable("uuid") uuid: UUID): String {
        return facade.load(uuid)
                .orElseThrow { NotFound(uuid) }
    }
}
