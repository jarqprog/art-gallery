package com.jarqprog.artapi.applicationservice.exceptions

class EventProcessingFailure(message: String, throwable: Throwable? = null) : RuntimeException(message, throwable) {

    companion object Factory {
        fun fromThrowable(throwable: Throwable): EventProcessingFailure {
            return EventProcessingFailure(throwable.localizedMessage, throwable)
        }
    }
}