package com.jarqprog.artapi.writedepricated.api

import com.jarqprog.artapi.writedepricated.api.art.ArtEntity
import com.jarqprog.artapi.writedepricated.api.art.ArtRepository
import com.jarqprog.artapi.writedepricated.api.artjson.ArtJson
import com.jarqprog.artapi.writedepricated.api.artjson.ArtJsonRepository
import com.jarqprog.artapi.writedepricated.domain.ArtModel
import com.jarqprog.artapi.writedepricated.dto.ArtDTO


class WritePlugin(
        private val artRepository: ArtRepository,
        private val artJsonRepository: ArtJsonRepository

) : WriteFacade {

    override fun saveOne(art: ArtDTO) {

        val model = ArtModel.fromArt(art)
        val entity = ArtEntity.fromModel(model)
        val dto = ArtDTO.fromModel(model)
        val json = ArtJson(dto.uuid(), dto)

        artRepository.save(entity)
        artJsonRepository.save(json)
    }
}