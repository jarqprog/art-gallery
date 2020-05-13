package com.jarqprog.artapi.command.api

import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.api.exceptions.EventProcessingFailure
import java.util.*

interface EventPublishing {

    fun publish(event: ArtEvent): Optional<EventProcessingFailure>

}