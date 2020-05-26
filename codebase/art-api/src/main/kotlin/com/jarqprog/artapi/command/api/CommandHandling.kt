package com.jarqprog.artapi.command.api

import com.jarqprog.artapi.command.api.commands.ArtCommand
import java.util.*

interface CommandHandling {

    fun handle(command: ArtCommand): Optional<Throwable>

}