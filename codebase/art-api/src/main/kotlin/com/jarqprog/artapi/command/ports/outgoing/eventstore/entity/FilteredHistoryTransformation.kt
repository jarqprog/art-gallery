package com.jarqprog.artapi.command.ports.outgoing.eventstore.entity

import com.jarqprog.artapi.command.domain.ArtHistory
import com.jarqprog.artapi.command.domain.vo.Identifier
import java.time.Instant
import java.util.function.BiFunction
import java.util.stream.Collectors

class FilteredHistoryTransformation : BiFunction<ArtHistoryDescriptor, Instant, ArtHistory> {

    private val descriptorToEvent = DescriptorToEvent()

    override fun apply(historyDescriptor: ArtHistoryDescriptor, stateAt: Instant): ArtHistory {

        val history = historyDescriptor.events()
                .stream()
                .filter { eventDescriptor -> eventDescriptor.isNotLaterThan(stateAt) }
                .map(descriptorToEvent)
                .collect(Collectors.toList())

        return ArtHistory(
                Identifier(historyDescriptor.artId),
                history
        )
    }
}
