package com.jarqprog.artapi.command.artdomain

import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.exceptions.EventProcessingFailure
import java.util.*

interface EventPublishing {

    fun publish(event: ArtEvent): Optional<EventProcessingFailure>

}