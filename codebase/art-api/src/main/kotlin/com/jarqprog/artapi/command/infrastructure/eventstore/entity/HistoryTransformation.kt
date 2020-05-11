package com.jarqprog.artapi.command.infrastructure.eventstore.entity

import com.jarqprog.artapi.command.artdomain.ArtHistory
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import java.util.function.Function
import java.util.stream.Collectors

class HistoryTransformation : Function<ArtHistoryDescriptor, ArtHistory> {

    private val descriptorToEvent = DescriptorToEvent()

    override fun apply(historyDescriptor: ArtHistoryDescriptor): ArtHistory {
        return ArtHistory(
                Identifier(historyDescriptor.artId),
                historyDescriptor.version,
                historyDescriptor.timestamp,
                historyDescriptor.events()
                        .stream()
                .map(descriptorToEvent)
                .collect(Collectors.toList())
        )
    }
}

