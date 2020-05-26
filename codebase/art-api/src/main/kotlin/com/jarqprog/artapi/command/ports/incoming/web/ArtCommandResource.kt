package com.jarqprog.artapi.command.ports.incoming.web

import com.jarqprog.artapi.command.api.CommandHandling
import com.jarqprog.artapi.command.api.commands.CreateArt
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.function.Function


@RestController
@RequestMapping("/api/art")
class ArtCommandResource(
        private val commandHandling: CommandHandling,
        private val errorResponseResolving: Function<Throwable,ResponseEntity<*>>
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun create(@RequestBody command: CreateArt): ResponseEntity<*> {
        return commandHandling.handle(command)
                .map {
                    failure -> logger.error("Error occurred on processing command: $command", failure)
                    failure
                }
                .map(errorResponseResolving)
                .orElse(ResponseEntity.accepted().build<String>())
    }
}