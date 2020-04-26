package com.jarqprog.artapi.write.database

import com.jarqprog.artapi.write.domain.Comment
import com.jarqprog.artapi.write.domain.CommentModel
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity

@Entity(name = "Comment")
class CommentEntity(
        uuid: UUID,
        date: LocalDateTime,
        archived: Boolean = false,
        private val author: String,
        private val content: String

) : BaseEntity(uuid, date, archived), Comment {

    override fun author() = author
    override fun content() = content

    companion object Factory {
        fun fromComment(comment: CommentModel): CommentEntity = CommentEntity(
                comment.uuid(),
                comment.date(),
                comment.archived(),
                comment.author(),
                comment.content()
        )
    }
}

