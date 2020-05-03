package com.jarqprog.artapi.write.domain

import com.jarqprog.artapi.UNDEFINED
import com.jarqprog.artapi.UNKNOWN
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

class ArtModel(

        uuid: UUID = UUID.randomUUID(),
        date: LocalDateTime = LocalDateTime.now(),
        archived: Boolean = false,
        metadata: MetadataModel = MetadataModel(dataUUID = uuid),
        private val author: String = UNKNOWN,
        private val path: String = UNDEFINED,
        private val comments: Set<CommentModel> = emptySet()

) : BaseModel(uuid, date, archived, metadata), Art {

    override fun author(): String = author

    override fun path(): String = path

    override fun comments(): Set<CommentModel> = comments

    override fun toString(): String {
        return "ArtModel(author='$author', path='$path', comments=$comments)"
    }

    companion object Factory {
        fun fromArt(art: Art): ArtModel = ArtModel(
                art.uuid(),
                art.date(),
                art.archived(),
                author = art.author(),
                path = art.path(),
                comments = asCommentsModel(art.comments())
        )

        private fun asCommentsModel(comments: Set<Comment>): Set<CommentModel> {
            return comments.stream()
                    .map { comment -> CommentModel.fromComment(comment) }
                    .collect(Collectors.toSet())
        }
    }


}
