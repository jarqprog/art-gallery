package com.jarqprog.artapi.command.ports.outgoing.eventstore.exceptions

import java.lang.RuntimeException

class EventStoreFailure(message: String, cause: Throwable? = null) : RuntimeException(message, cause)