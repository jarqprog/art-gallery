package com.jarqprog.artapi.support

import com.jarqprog.artapi.applicationservice.commands.CreateArt
import com.jarqprog.artapi.domain.ArtGenre
import com.jarqprog.artapi.domain.ArtStatus
import com.jarqprog.artapi.support.EventContainer.ANY_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.ANY_RESOURCE
import com.jarqprog.artapi.support.EventContainer.AUTHOR_MARIA
import com.jarqprog.artapi.support.EventContainer.USER_MARIA

object CommandSupport {

    val CREATE_ART = CreateArt(
            ANY_IDENTIFIER,
            AUTHOR_MARIA,
            ANY_RESOURCE,
            USER_MARIA,
            ArtGenre.UNDEFINED,
            ArtStatus.ACTIVE
    )
}