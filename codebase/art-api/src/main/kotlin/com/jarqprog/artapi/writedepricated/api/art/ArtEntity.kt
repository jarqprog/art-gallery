package com.jarqprog.artapi.writedepricated.api.art

import com.jarqprog.artapi.writedepricated.domain.Art
import com.jarqprog.artapi.writedepricated.domain.ArtModel
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors
import javax.persistence.*

@Entity(name = "Art")
class ArtEntity(

        uuid: UUID = UUID.randomUUID(),
        date: LocalDateTime = LocalDateTime.now(),
        archived: Boolean = false,
        metadata: MetadataEntity,

        @Column(name = "AUTHOR")
        private val author: String,

        @Column(name = "PATH")
        private val path: String,

        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
        @JoinColumn(name = "ART_UUID", referencedColumnName = "UUID")
        private val comments: Set<CommentEntity> = mutableSetOf()

) : BaseEntity(uuid, date, archived, metadata), Art {

    override fun author(): String = author

    override fun path(): String = path

    override fun comments(): Set<CommentEntity> = comments

    companion object Factory {
        fun fromModel(art: ArtModel): ArtEntity = ArtEntity(
                art.uuid(),
                art.date(),
                art.archived(),
                MetadataEntity.fromModel(art),
                author = art.author(),
                path = art.path(),
                comments = asCommentsEntities(art)
        )

        private fun asCommentsEntities(art: ArtModel): Set<CommentEntity> {
            return art.comments().stream()
                    .map { comment -> CommentEntity.fromModel(comment) }
                    .collect(Collectors.toSet())
        }
    }
}
