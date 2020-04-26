package com.jarqprog.artapi.write.api.artjson

import org.springframework.data.repository.CrudRepository
import java.util.*

interface ArtJsonRepository: CrudRepository<ArtJson, UUID>