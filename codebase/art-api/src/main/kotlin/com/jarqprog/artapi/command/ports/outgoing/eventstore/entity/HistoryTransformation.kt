package com.jarqprog.artapi.command.ports.outgoing.eventstore.entity

import com.jarqprog.artapi.domain.ArtHistory
import java.util.function.Function
import java.util.stream.Collectors

class HistoryTransformation : Function<ArtHistoryDescriptor, ArtHistory> {

    private val descriptorToEvent = ToEvent()

    override fun apply(historyDescriptor: ArtHistoryDescriptor): ArtHistory {
        return ArtHistory.withEvents(
                historyDescriptor.events()
                        .stream()
                        .map(descriptorToEvent)
                        .collect(Collectors.toList())
        )
    }
}

