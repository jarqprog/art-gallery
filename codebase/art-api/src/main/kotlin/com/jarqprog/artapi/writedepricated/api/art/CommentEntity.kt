package com.jarqprog.artapi.writedepricated.api.art

import com.jarqprog.artapi.writedepricated.domain.Comment
import com.jarqprog.artapi.writedepricated.domain.CommentModel
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Comment")
class CommentEntity(
        uuid: UUID,
        date: LocalDateTime,
        archived: Boolean = false,
        metadataEntity: MetadataEntity,

        @Column(name = "AUTHOR")
        private val author: String,

        @Column(name = "CONTENT")
        private val content: String,

        @Column(name = "ART_UUID")
        private val artUUID: UUID

) : BaseEntity(uuid, date, archived, metadataEntity), Comment {

    override fun author() = author
    override fun content() = content
    override fun artUUID(): UUID = artUUID

    companion object Factory {
        fun fromModel(comment: CommentModel): CommentEntity = CommentEntity(
                comment.uuid(),
                comment.date(),
                comment.archived(),
                MetadataEntity.fromModel(comment),
                author = comment.author(),
                content = comment.content(),
                artUUID = comment.artUUID()
        )
    }
}

