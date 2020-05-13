package com.jarqprog.artapi.writedepricated.api

import com.jarqprog.artapi.writedepricated.dto.ArtDTO

interface WriteFacade {

    fun saveOne(art: ArtDTO)

}