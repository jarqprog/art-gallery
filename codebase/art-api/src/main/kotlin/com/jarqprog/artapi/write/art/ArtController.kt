package com.jarqprog.artapi.write.art

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.jarqprog.artapi.write.database.JsonComment
import com.jarqprog.artapi.write.database.ArtRepository
import com.jarqprog.artapi.write.database.CommentEntity
import com.jarqprog.artapi.write.database.JsonCommentRepository
import com.jarqprog.artapi.write.domain.CommentModel
import com.jarqprog.artapi.write.dto.CommentDTO
import org.postgresql.util.PGobject
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/art")
class ArtController(private val artRepository: ArtRepository,
                    private val readRepo: JsonCommentRepository) {


    private val mapper = ObjectMapper().registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @PostMapping
    fun save(@RequestBody comment: CommentDTO) {
        //todo temporary
        val model = CommentModel.fromComment(comment)
        val entity = CommentEntity.fromComment(model)

        readRepo.save(JsonComment(entity.uuid(), CommentDTO.fromComment(model)))
        artRepository.save(entity)
    }
//    mapper.writeValueAsString(CommentDTO.fromComment(model)))
}