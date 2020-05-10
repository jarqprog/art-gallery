package com.jarqprog.artapi.command.artdomain.exceptions

class CommandProcessingFailure(message: String, throwable: Throwable? = null) : RuntimeException(message, throwable) {

    companion object Factory {
        fun fromThrowable(throwable: Throwable): CommandProcessingFailure {
            return CommandProcessingFailure(throwable.localizedMessage, throwable)
        }
    }
}