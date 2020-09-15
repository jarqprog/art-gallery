package com.jarqprog.artapi.ports.incoming.web

import com.jarqprog.artapi.domain.CommandHandling
import com.jarqprog.artapi.applicationservice.commands.ChangeResource
import com.jarqprog.artapi.applicationservice.commands.CreateArt
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.function.Function


@RestController
@RequestMapping("/api/art")
class ArtCommandResource(
        private val commandHandling: CommandHandling,
        private val errorResponseResolving: Function<Throwable, Mono<ResponseEntity<String>>>
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun create(@RequestBody command: CreateArt): Mono<ResponseEntity<String>> {
        return commandHandling.handle(command)
                .doOnNext { event -> logger.debug("Produced $event") }
                .map { ResponseEntity.accepted().build<String>() }
                .doOnError { failure -> logger.error("Error occurred on processing command: $command", failure) }
                .onErrorResume(errorResponseResolving)
    }

    @PutMapping
    fun changeResource(@RequestBody command: ChangeResource): Mono<ResponseEntity<String>> {
        return commandHandling.handle(command)
                .doOnNext { event -> logger.debug("Produced $event") }
                .map { ResponseEntity.accepted().build<String>() }
                .doOnError { failure -> logger.error("Error occurred on processing command: $command", failure) }
                .onErrorResume(errorResponseResolving)
    }
}