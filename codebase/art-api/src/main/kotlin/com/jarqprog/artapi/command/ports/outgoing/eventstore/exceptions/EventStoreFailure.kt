package com.jarqprog.artapi.command.ports.outgoing.eventstore.exceptions

import com.jarqprog.artapi.domain.vo.Identifier
import java.lang.RuntimeException

class EventStoreFailure(message: String, cause: Throwable? = null) : RuntimeException(message, cause) {

    companion object Factory {

        fun fromException(throwable: Throwable) = EventStoreFailure(throwable.localizedMessage, throwable)
        fun notFoundBy(artIdentifier: Identifier) = EventStoreFailure("Not found by $artIdentifier")
    }
}