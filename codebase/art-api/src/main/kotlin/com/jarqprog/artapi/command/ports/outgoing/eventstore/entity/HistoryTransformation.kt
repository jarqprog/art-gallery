package com.jarqprog.artapi.command.ports.outgoing.eventstore.entity

import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.vo.Identifier
import java.util.function.Function
import java.util.stream.Collectors

class HistoryTransformation : Function<ArtHistoryDescriptor, ArtHistory> {

    private val descriptorToEvent = DescriptorToEvent()

    override fun apply(historyDescriptor: ArtHistoryDescriptor): ArtHistory {
        return ArtHistory(
                Identifier(historyDescriptor.artId),
                historyDescriptor.events()
                        .stream()
                        .map(descriptorToEvent)
                        .collect(Collectors.toList())
        )
    }
}

