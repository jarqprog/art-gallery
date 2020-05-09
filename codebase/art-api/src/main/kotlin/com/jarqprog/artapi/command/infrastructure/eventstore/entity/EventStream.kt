package com.jarqprog.artapi.command.infrastructure.eventstore.entity

import javax.persistence.*

@Entity(name = "ART_EVENT_STREAM")
data class EventStream(

        @Id
        val artId: String,

        @Version
        val version: Int,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinColumn(name = "artId", referencedColumnName = "artId")
        private val events: List<EventDescriptor>

) {
    fun events() = events

    fun add(event: EventDescriptor): EventStream {
        val newEvents = events.toMutableList()
        newEvents.add(event)
        return EventStream(
                artId,
                version + 1,
                newEvents.toList()
        )
    }
}