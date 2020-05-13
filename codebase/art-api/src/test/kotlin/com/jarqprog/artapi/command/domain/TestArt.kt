package com.jarqprog.artapi.command.domain


import com.jarqprog.artapi.command.domain.vo.Author
import com.jarqprog.artapi.command.domain.vo.Identifier
import com.jarqprog.artapi.command.domain.vo.Resource
import com.jarqprog.artapi.command.domain.vo.User
import java.time.Instant

internal class TestArt (
        private val identifier: Identifier,
        private val version: Int,
        private val timestamp: Instant,
        private val author: Author,
        private val resource: Resource,
        private val addedBy: User,
        private val genre: ArtGenre,
        private val status: ArtStatus
) {
    fun identifier() = identifier
    fun version() = version
    fun timestamp() = timestamp
    fun author() = author
    fun resource() = resource
    fun addedBy() = addedBy
    fun genre() = genre
    fun status() = status
}