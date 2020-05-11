package com.jarqprog.artapi.command.infrastructure.eventstore.entity

import com.jarqprog.artapi.command.artdomain.ArtHistory
import java.time.Instant
import java.util.function.BiFunction
import java.util.stream.Collectors

class FilteredHistoryTransformation : BiFunction<ArtHistoryDescriptor, Instant, ArtHistory> {

    private val descriptorToEvent = DescriptorToEvent()

    override fun apply(historyDescriptor: ArtHistoryDescriptor, stateAt: Instant): ArtHistory {

        val history = historyDescriptor.events()
                .stream()
                .filter { eventDescriptor -> eventDescriptor.isNotLaterThan(stateAt) }
                .sorted(Comparator.comparing(ArtEventDescriptor::timestamp))
                .map(descriptorToEvent)
                .collect(Collectors.toList())

        val last = history.last()

        return ArtHistory(
                last.artId(),
                last.version(),
                last.timestamp(),
                history
        )
    }
}
