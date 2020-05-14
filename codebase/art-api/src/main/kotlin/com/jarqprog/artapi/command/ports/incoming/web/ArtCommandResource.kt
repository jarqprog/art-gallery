package com.jarqprog.artapi.command.ports.incoming.web

import com.jarqprog.artapi.command.api.CommandHandling
import com.jarqprog.artapi.command.api.exceptions.CommandProcessingFailure
import com.jarqprog.artapi.command.api.exceptions.IncorrectVersion
import com.jarqprog.artapi.command.api.exceptions.NotFound
import com.jarqprog.artapi.command.domain.commands.CreateArt
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/art")
class ArtCommandResource(private val commandHandling: CommandHandling) {

    @PostMapping
    fun create(@RequestBody command: CreateArt): ResponseEntity<*> {
        return commandHandling.handle(command)
                .map(this::resolveErrorResponse)
                .orElseGet { ResponseEntity.accepted().build() }
    }

    private fun resolveErrorResponse(throwable: Throwable) = when(throwable) {
            is CommandProcessingFailure -> 400
            is IncorrectVersion -> 400
            is NotFound -> 404
            else -> 500
        }.let { code -> ResponseEntity.status(code).body(ErrorResponseBody(throwable.localizedMessage)) }
}