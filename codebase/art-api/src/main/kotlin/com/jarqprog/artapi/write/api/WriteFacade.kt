package com.jarqprog.artapi.write.api

import com.jarqprog.artapi.write.dto.ArtDTO

interface WriteFacade {

    fun saveOne(art: ArtDTO)

}