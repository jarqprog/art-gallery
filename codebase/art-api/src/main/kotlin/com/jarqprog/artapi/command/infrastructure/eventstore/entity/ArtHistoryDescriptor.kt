package com.jarqprog.artapi.command.infrastructure.eventstore.entity

import java.time.Instant
import javax.persistence.*

@Entity(name = "ART_EVENT_STREAM")
data class ArtHistoryDescriptor(

        @Id
        val artId: String,

        @Version
        val version: Int,

        val timestamp: Instant,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinColumn(name = "artId", referencedColumnName = "artId")
        private val events: List<ArtEventDescriptor>

) {
    fun events() = events

    fun add(event: ArtEventDescriptor): ArtHistoryDescriptor {
        val newEvents = events.toMutableList().plus(event)
//        newEvents.add(event)
        return ArtHistoryDescriptor(
                artId,
                version + 1,
                event.timestamp,
                newEvents
        )
    }
}