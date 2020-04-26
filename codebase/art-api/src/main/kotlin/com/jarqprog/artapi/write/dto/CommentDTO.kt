package com.jarqprog.artapi.write.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.jarqprog.artapi.write.domain.ANONYMOUS
import com.jarqprog.artapi.write.domain.Comment
import com.jarqprog.artapi.write.domain.UNDEFINED
import java.time.LocalDateTime
import java.util.*

class CommentDTO(
        uuid: UUID = UUID.randomUUID(),
        date: LocalDateTime = LocalDateTime.now(),
        archived: Boolean = false,
        private val author: String = ANONYMOUS,
        private val content: String = UNDEFINED

) : BaseDTO(uuid, date, archived), Comment {

    @JsonProperty
    override fun author() = author

    @JsonProperty
    override fun content() = content

    companion object Factory {
        fun fromComment(comment: Comment): CommentDTO = CommentDTO(
                comment.uuid(),
                comment.date(),
                comment.archived(),
                comment.author(),
                comment.content()
        )
    }
}