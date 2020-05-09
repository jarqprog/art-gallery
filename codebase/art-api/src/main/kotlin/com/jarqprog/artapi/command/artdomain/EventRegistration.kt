package com.jarqprog.artapi.command.artdomain

import com.jarqprog.artapi.command.artdomain.events.ArtEvent

interface EventRegistration {

    fun register(event: ArtEvent)

}