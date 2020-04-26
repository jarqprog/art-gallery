package com.jarqprog.artapi.read

import com.jarqprog.artapi.read.database.ReadDatabaseFacade
import com.jarqprog.artapi.read.exceptions.NotFound
import com.jarqprog.artapi.write.dto.CommentDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/art")
class ApiEndpoints(@Autowired private val databaseFacade: ReadDatabaseFacade) {

    @GetMapping("/{uuid}")
    fun find(@PathVariable("uuid") uuid: UUID): CommentDTO {
        return databaseFacade.load(uuid)
                .orElseThrow { NotFound(uuid) }
    }
}
