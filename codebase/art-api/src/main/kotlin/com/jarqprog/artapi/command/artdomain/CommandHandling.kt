package com.jarqprog.artapi.command.artdomain

import com.jarqprog.artapi.command.artdomain.commands.ArtCommand
import com.jarqprog.artapi.command.artdomain.exceptions.CommandProcessingFailure
import java.util.*

interface CommandHandling {

    fun handle(command: ArtCommand): Optional<CommandProcessingFailure>

}