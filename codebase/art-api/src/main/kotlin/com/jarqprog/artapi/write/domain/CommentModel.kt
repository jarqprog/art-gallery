package com.jarqprog.artapi.write.domain

import java.time.LocalDateTime
import java.util.*

class CommentModel(
        uuid: UUID = UUID.randomUUID(),
        date: LocalDateTime = LocalDateTime.now(),
        archived: Boolean = false,
        private val author: String = ANONYMOUS,
        private val content: String = UNDEFINED

) : BaseModel(uuid, date, archived), Comment {

    override fun author() = author
    override fun content() = content

    companion object Factory {
        fun fromComment(comment: Comment): CommentModel = CommentModel(
                comment.uuid(),
                comment.date(),
                comment.archived(),
                comment.author(),
                comment.content()
        )
    }
}
