package com.jarqprog.artapi.support


import com.jarqprog.artapi.domain.ArtGenre
import com.jarqprog.artapi.domain.ArtStatus
import com.jarqprog.artapi.domain.vo.Author
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.domain.vo.User
import java.time.Instant

data class TestArt(
        val identifier: Identifier,
        val version: Int,
        val timestamp: Instant,
        val author: Author,
        val resource: Resource,
        val addedBy: User,
        val genre: ArtGenre,
        val status: ArtStatus
)