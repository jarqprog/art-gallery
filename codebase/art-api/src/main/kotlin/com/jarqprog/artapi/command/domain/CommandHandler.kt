package com.jarqprog.artapi.command.domain

import com.jarqprog.artapi.command.domain.commands.ArtCommand
import com.jarqprog.artapi.command.domain.events.ArtEvent

interface CommandHandler {

    fun handle(command: ArtCommand): ArtEvent

    fun handle(command: ArtCommand, history: List<ArtEvent>): ArtEvent

}