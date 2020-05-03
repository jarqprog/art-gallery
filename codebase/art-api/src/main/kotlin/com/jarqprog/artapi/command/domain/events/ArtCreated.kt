package com.jarqprog.artapi.command.domain.events

import com.jarqprog.artapi.command.domain.ArtGenre
import com.jarqprog.artapi.command.domain.ArtStatus
import com.jarqprog.artapi.command.domain.vo.Author
import com.jarqprog.artapi.command.domain.vo.Resource
import com.jarqprog.artapi.command.domain.vo.User
import java.time.Instant
import java.util.*

class ArtCreated(

        artUuid: UUID,
        version: Int,
        timestamp: Instant,
        private val author: Author,
        private val resource: Resource,
        private val addedBy: User,
        private val artGenre: ArtGenre,
        private val artStatus: ArtStatus

): ArtEvent(artUuid, version, timestamp) {

        fun author() = author
        fun resource() = resource
        fun addedBy() = addedBy
        fun artGenre() = artGenre
        fun artStatus() = artStatus

}


