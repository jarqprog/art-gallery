package com.jarqprog.artapi.read.database

import com.jarqprog.artapi.write.dto.CommentDTO
import java.util.UUID
import java.util.Optional

interface ReadDatabaseFacade {

    fun load(uuid: UUID): Optional<CommentDTO>

}