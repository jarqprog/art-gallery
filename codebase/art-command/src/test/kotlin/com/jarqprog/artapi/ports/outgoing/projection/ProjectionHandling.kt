package com.jarqprog.artapi.ports.outgoing.projection


import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.ports.outgoing.projection.exception.ProjectionHandlingFailure
import java.util.*

interface ProjectionHandling {

    fun handle(artIdentifier: Identifier): Optional<ProjectionHandlingFailure>

}