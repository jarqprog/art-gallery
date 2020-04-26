package com.jarqprog.artapi.write.database

import com.jarqprog.artapi.write.dto.CommentDTO
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name="JsonComment")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class JsonComment(

        @Id
        private val uuid: UUID,

        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb")
        val comment: CommentDTO
)