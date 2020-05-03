package com.jarqprog.artapi.write.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.jarqprog.artapi.UNDEFINED
import com.jarqprog.artapi.UNKNOWN
import com.jarqprog.artapi.write.domain.*
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

class ArtDTO (
        uuid: UUID = UUID.randomUUID(),
        date: LocalDateTime = LocalDateTime.now(),
        archived: Boolean = false,
        private val author: String = UNKNOWN,
        private val path: String = UNDEFINED,
        private val comments: Set<CommentDTO> = emptySet()

): BaseDTO(uuid, date, archived), Art {

    @JsonProperty
    override fun author(): String = author

    @JsonProperty
    override fun path(): String = path

    @JsonProperty
    override fun comments(): Set<CommentDTO> = comments

    companion object Factory {
        fun fromModel(art: ArtModel): ArtDTO = ArtDTO(
                art.uuid(),
                art.date(),
                art.archived(),
                art.author(),
                art.path(),
                asCommentsModel(art.comments())
        )

        private fun asCommentsModel(comments: Set<CommentModel>): Set<CommentDTO> {
            return comments.stream()
                    .map { comment -> CommentDTO.fromCommentModel(comment) }
                    .collect(Collectors.toSet())
        }
    }
}