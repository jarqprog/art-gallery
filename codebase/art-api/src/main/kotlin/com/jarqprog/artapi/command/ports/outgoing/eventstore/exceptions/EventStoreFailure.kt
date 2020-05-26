package com.jarqprog.artapi.command.ports.outgoing.eventstore.exceptions

import java.lang.RuntimeException

class EventStoreFailure(message: String, cause: Throwable? = null) : RuntimeException(message, cause) {

    companion object Factory {

        fun fromException(throwable: Throwable) = EventStoreFailure(throwable.localizedMessage, throwable)
    }
}