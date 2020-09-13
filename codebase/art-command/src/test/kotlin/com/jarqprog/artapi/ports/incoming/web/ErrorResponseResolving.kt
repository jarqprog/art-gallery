package com.jarqprog.artapi.ports.incoming.web

import com.jarqprog.artapi.applicationservice.handler.exceptions.CommandProcessingFailure
import com.jarqprog.artapi.applicationservice.handler.exceptions.IncorrectVersion
import com.jarqprog.artapi.applicationservice.handler.exceptions.NotFound
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import java.util.function.Function

class ErrorResponseResolving : Function<Throwable, Mono<ResponseEntity<String>>> {

    override fun apply(throwable: Throwable): Mono<ResponseEntity<String>> = when (throwable) {
        is CommandProcessingFailure -> 400
        is IncorrectVersion -> 400
        is NotFound -> 404
        else -> 500
    }.let { code -> Mono.just(ResponseEntity.status(code).body(throwable.localizedMessage)) }
}