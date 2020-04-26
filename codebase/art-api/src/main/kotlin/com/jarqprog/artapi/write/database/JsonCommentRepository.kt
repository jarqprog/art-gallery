package com.jarqprog.artapi.write.database

import org.springframework.data.repository.CrudRepository
import java.util.*

interface JsonCommentRepository : CrudRepository<JsonComment, UUID>