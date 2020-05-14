package com.jarqprog.artapi.command.api

import com.jarqprog.artapi.command.domain.commands.ArtCommand
import com.jarqprog.artapi.command.api.exceptions.CommandProcessingFailure
import java.util.*

interface CommandHandling {

    fun handle(command: ArtCommand): Optional<Throwable>

}