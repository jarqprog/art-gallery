package com.jarqprog.artapi.write.api.art

import org.springframework.data.repository.CrudRepository
import java.util.*

interface ArtRepository: CrudRepository<ArtEntity,UUID>