package com.jarqprog.artapi.writedepricated.api.art

import org.springframework.data.repository.CrudRepository
import java.util.*

interface ArtRepository : CrudRepository<ArtEntity, UUID>