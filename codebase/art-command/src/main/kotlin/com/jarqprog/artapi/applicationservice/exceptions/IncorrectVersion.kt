package com.jarqprog.artapi.applicationservice.exceptions

import com.jarqprog.artapi.domain.events.ArtEvent

class IncorrectVersion(message: String) : RuntimeException(message) {

    companion object Factory {

        fun fromEvent(artEvent: ArtEvent) = IncorrectVersion("incorrect version for $artEvent")
    }
}