package com.jarqprog.artapi.writedepricated.api.art

import com.jarqprog.artapi.writedepricated.domain.Metadata
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity(

        @Id
        @Column(name = "UUID")
        private val uuid: UUID,

        @Column(name = "DATE")
        private val date: LocalDateTime,

        @Column(name = "ARCHIVED")
        private val archived: Boolean = false,

        @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
        @JoinColumn(name = "DATA_UUID")
        private val metadata: MetadataEntity
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseEntity) return false
        return uuid == other.uuid
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }

    fun uuid() = uuid
    fun date() = date
    fun archived() = archived
    fun metadata(): Metadata = metadata
}