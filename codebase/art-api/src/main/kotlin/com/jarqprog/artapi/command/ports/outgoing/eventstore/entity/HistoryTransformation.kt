package com.jarqprog.artapi.command.ports.outgoing.eventstore.entity

import com.jarqprog.artapi.domain.ArtHistory
import java.util.*
import java.util.function.BiFunction
import java.util.stream.Collectors

class HistoryTransformation : BiFunction<ArtHistoryDescriptor, Optional<Snapshot>, ArtHistory> {

    private val descriptorToEvent = ToEvent()
    private val snapshotToArt = SnapshotToArt()

    override fun apply(historyDescriptor: ArtHistoryDescriptor, optionalSnapshot: Optional<Snapshot>): ArtHistory {
        return optionalSnapshot
                .map { snapshot ->
                    ArtHistory.withSnapshot(
                            snapshotToArt.apply(snapshot),
                            historyDescriptor.events()
                                    .stream()
                                    .filter { event -> event.isNotLaterThan(snapshot.timestamp) }
                                    .map(descriptorToEvent)
                                    .collect(Collectors.toList())
                    )
                }
                .orElseGet {
                    ArtHistory.withEvents(
                            historyDescriptor.events()
                                    .stream()
                                    .map(descriptorToEvent)
                                    .collect(Collectors.toList())
                    )
                }
    }
}
