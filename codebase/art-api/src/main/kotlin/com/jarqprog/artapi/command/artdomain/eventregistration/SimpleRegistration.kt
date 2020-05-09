package com.jarqprog.artapi.command.artdomain.eventregistration

import com.jarqprog.artapi.command.artdomain.EventRegistration
import com.jarqprog.artapi.command.artdomain.EventStore
import com.jarqprog.artapi.command.artdomain.events.ArtEvent

class SimpleRegistration(private val artEventStorage: EventStore) : EventRegistration {

    override fun register(event: ArtEvent) {
        artEventStorage.save(event)
    }
}