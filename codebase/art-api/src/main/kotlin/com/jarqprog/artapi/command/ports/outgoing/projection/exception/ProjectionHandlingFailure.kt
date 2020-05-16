package com.jarqprog.artapi.command.ports.outgoing.projection.exception

import java.lang.RuntimeException

class ProjectionHandlingFailure(message: String, cause: Throwable? = null) : RuntimeException(message, cause) {

    companion object Factory {

        fun fromException(throwable: Throwable) = ProjectionHandlingFailure(throwable.localizedMessage, throwable)
    }
}