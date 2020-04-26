package com.jarqprog.artapi.write.database

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ArtRepository : CrudRepository<CommentEntity,UUID>