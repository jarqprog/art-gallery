package com.jarqprog.artapi.domain

import com.jarqprog.artapi.domain.art.ProcessingResult
import reactor.core.publisher.Mono

interface ChangesPublishing {
    fun publish(processingResult: ProcessingResult): Mono<Void>
}