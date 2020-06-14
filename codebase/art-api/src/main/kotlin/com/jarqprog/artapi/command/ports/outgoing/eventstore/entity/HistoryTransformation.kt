package com.jarqprog.artapi.command.ports.outgoing.eventstore.entity

import com.jarqprog.artapi.domain.ArtHistory
import java.util.*
import java.util.stream.Collectors

class HistoryTransformation {

    private val descriptorToEvent = ToEvent()
    private val snapshotToArt = SnapshotToArt()

    fun asHistory(events: List<EventDescriptor>, optionalSnapshot: Optional<Snapshot>): ArtHistory {
        return optionalSnapshot
                .map { snapshot ->
                    ArtHistory.withSnapshot(
                            snapshotToArt.apply(snapshot),
                            events
                                    .stream()
                                    .filter { event -> event.isNotLaterThan(snapshot.timestamp) }
                                    .map(descriptorToEvent)
                                    .collect(Collectors.toList())
                    )
                }
                .orElseGet {
                    ArtHistory.withEvents(
                            events
                                    .stream()
                                    .map(descriptorToEvent)
                                    .collect(Collectors.toList())
                    )
                }
    }
}
