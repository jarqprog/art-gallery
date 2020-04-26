package com.jarqprog.artapi.write.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.jarqprog.artapi.write.api.art.ArtEntity
import com.jarqprog.artapi.write.api.art.ArtRepository
import com.jarqprog.artapi.write.api.artjson.ArtJson
import com.jarqprog.artapi.write.api.artjson.ArtJsonRepository
import com.jarqprog.artapi.write.domain.ArtModel
import com.jarqprog.artapi.write.dto.ArtDTO


class WritePlugin(
        private val artRepository: ArtRepository,
        private val artJsonRepository: ArtJsonRepository

): WriteFacade {

    override fun saveOne(art: ArtDTO) {

        val model = ArtModel.fromArt(art)
        val entity = ArtEntity.fromModel(model)
        val dto = ArtDTO.fromModel(model)
        val json = ArtJson(dto.uuid(), dto)

        artRepository.save(entity)
        artJsonRepository.save(json)
    }
}