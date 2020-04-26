package com.jarqprog.artapi.write.domain

import java.time.LocalDateTime
import java.util.*

class CommentModel(
        uuid: UUID = UUID.randomUUID(),
        date: LocalDateTime = LocalDateTime.now(),
        archived: Boolean = false,
        metadata: MetadataModel = MetadataModel(dataUUID = uuid),
        private val author: String = ANONYMOUS,
        private val content: String = UNDEFINED,
        private val artUUID: UUID

) : BaseModel(uuid, date, archived, metadata), Comment {

    override fun author() = author
    override fun content() = content
    override fun artUUID(): UUID = artUUID

    companion object Factory {
        fun fromComment(comment: Comment): CommentModel = CommentModel(
                comment.uuid(),
                comment.date(),
                comment.archived(),
                author = comment.author(),
                content = comment.content(),
                artUUID = comment.artUUID()
        )
    }
}
