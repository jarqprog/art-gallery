package com.jarqprog.artapi.applicationservice

import com.jarqprog.artapi.domain.art.ArtAggregate
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.vo.Identifier

data class ProcessingResult(val artEvent: ArtEvent, val currentState: ArtAggregate) {
    fun artIdentifier(): Identifier = artEvent.artId()
}