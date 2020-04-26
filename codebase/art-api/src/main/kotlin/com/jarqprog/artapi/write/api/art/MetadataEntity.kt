package com.jarqprog.artapi.write.api.art

import com.jarqprog.artapi.write.domain.BaseModel
import com.jarqprog.artapi.write.domain.Metadata
import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import java.util.UUID
import javax.persistence.*


@Entity(name = "METADATA")
class MetadataEntity (

        @Id
        @Column(name = "UUID")
        private val uuid: UUID,

        @Column(name = "DATA_UUID")
        private val dataUUID: UUID,

        @Column(name = "CREATED", nullable = false, updatable = false)
        @CreatedDate
        private val created: Instant,

        @Column(name = "INFO")
        private val info: String,

        @Column(name = "CLIENT_UUID")
        private val clientUUID: UUID

): Metadata {

    override fun uuid(): UUID = uuid

    override fun dataUUID(): UUID = dataUUID

    override fun created(): Instant = created

    override fun info(): String = info

    override fun clientUUID(): UUID = clientUUID

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MetadataEntity) return false
        return dataUUID == other.dataUUID
    }

    override fun hashCode(): Int {
        return dataUUID.hashCode()
    }

    companion object Factory {
        fun fromModel(model: BaseModel): MetadataEntity {
            val metadata = model.metadata()
            return MetadataEntity(
                    metadata.uuid(),
                    metadata.dataUUID(),
                    metadata.created(),
                    metadata.info(),
                    metadata.clientUUID()
            )
        }
    }
}