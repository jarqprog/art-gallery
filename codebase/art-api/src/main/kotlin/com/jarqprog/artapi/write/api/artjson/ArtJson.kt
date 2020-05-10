package com.jarqprog.artapi.write.api.artjson

import com.jarqprog.artapi.write.dto.ArtDTO
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "JSON_ART")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class ArtJson(

        @Id
        @Column(name = "UUID")
        private val uuid: UUID,

        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb", name = "ART")
        val art: ArtDTO
)