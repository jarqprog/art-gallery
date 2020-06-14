package com.jarqprog.artapi.command.ports.incoming

import com.jarqprog.artapi.command.ports.incoming.web.ErrorResponseResolving
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import java.util.function.Function

@Configuration
class IncomingConfig {

    @Bean
    fun errorResponseResolving(): Function<Throwable, Mono<ResponseEntity<String>>> = ErrorResponseResolving()
}