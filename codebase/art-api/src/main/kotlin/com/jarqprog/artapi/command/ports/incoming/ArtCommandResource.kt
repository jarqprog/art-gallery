package com.jarqprog.artapi.command.ports.incoming

import com.jarqprog.artapi.command.api.CommandHandling
import com.jarqprog.artapi.command.domain.commands.CreateArt
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/art")
class ArtCommandResource(private val commandHandling: CommandHandling) {

    @PostMapping
    fun create(@RequestBody command: CreateArt): ResponseEntity<*> {
        return commandHandling.handle(command)
                .map { failure -> ResponseEntity.status(500).body(failure.localizedMessage) }//todo
                .orElseGet { ResponseEntity.accepted().build() }
    }
}