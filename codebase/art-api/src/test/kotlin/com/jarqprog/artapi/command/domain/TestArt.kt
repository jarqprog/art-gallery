package com.jarqprog.artapi.command.domain


import com.jarqprog.artapi.command.domain.vo.Author
import com.jarqprog.artapi.command.domain.vo.Identifier
import com.jarqprog.artapi.command.domain.vo.Resource
import com.jarqprog.artapi.command.domain.vo.User
import java.time.Instant

internal data class TestArt(
        val identifier: Identifier,
        val version: Int,
        val timestamp: Instant,
        val author: Author,
        val resource: Resource,
        val addedBy: User,
        val genre: ArtGenre,
        val status: ArtStatus
)