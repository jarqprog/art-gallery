package com.jarqprog.artapi.command.api.exceptions

import com.jarqprog.artapi.domain.events.ArtEvent

class IncorrectVersion(message: String) : RuntimeException(message) {

    companion object Factory {

        fun fromEvent(artEvent: ArtEvent) = IncorrectVersion("incorrect version for $artEvent")
    }
}