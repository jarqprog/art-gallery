package com.jarqprog.artapi.command.ports.incoming.web

import com.jarqprog.artapi.command.api.exceptions.CommandProcessingFailure
import com.jarqprog.artapi.command.api.exceptions.IncorrectVersion
import com.jarqprog.artapi.command.api.exceptions.NotFound
import org.springframework.http.ResponseEntity
import java.util.function.Function

class ErrorResponseResolving : Function<Throwable,ResponseEntity<*>> {

    override fun apply(throwable: Throwable): ResponseEntity<*> = when (throwable) {
        is CommandProcessingFailure -> 400
        is IncorrectVersion -> 400
        is NotFound -> 404
        else -> 500
    }.let { code -> ResponseEntity.status(code).body(ErrorResponseBody(throwable.localizedMessage)) }
}

data class ErrorResponseBody(val errorMessage: String)