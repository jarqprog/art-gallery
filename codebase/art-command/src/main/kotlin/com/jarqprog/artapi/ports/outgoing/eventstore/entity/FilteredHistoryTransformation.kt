package com.jarqprog.artapi.ports.outgoing.eventstore.entity

import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.vo.Identifier
import java.time.Instant
import java.util.stream.Collectors

class FilteredHistoryTransformation {

    private val descriptorToEvent = ToEvent()

    fun asHistory(artId: Identifier, events: List<EventDescriptor>, stateAt: Instant): ArtHistory {

        val history = events.stream()
                .filter { eventDescriptor -> eventDescriptor.isNotLaterThan(stateAt) }
                .map(descriptorToEvent)
                .collect(Collectors.toList())

        return when(history.isEmpty()) {
            true -> ArtHistory.initialize(artId)
            else -> ArtHistory.withEvents(history)
        }
    }
}
