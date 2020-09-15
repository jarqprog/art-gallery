package com.jarqprog.artapi.domain.art

import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.domain.events.EventDescriptor
import kotlin.streams.toList

object ArtTestUtils {

    fun eventsToDescriptors(events: List<ArtEvent>): List<EventDescriptor> = events.stream()
            .map(ArtEvent::asDescriptor)
            .toList()
}