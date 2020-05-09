package com.jarqprog.artapi.command.infrastructure.eventstore.exceptions

import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import java.lang.RuntimeException

class EventStoreFailure(message: String, cause: Throwable? = null) : RuntimeException(message, cause) {

    companion object Factory {

        fun notFound(artId: Identifier) = EventStoreFailure("Not found by art identifier=$artId")

        fun concurrentWrite(artEvent: ArtEvent) = EventStoreFailure(
                "event ${artEvent.eventName()} with version:${artEvent.version()}" +
                        "for art uuid: ${artEvent.artId()}" +
                        " already exists")
    }
}