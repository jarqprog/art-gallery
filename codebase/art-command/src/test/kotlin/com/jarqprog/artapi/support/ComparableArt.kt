package com.jarqprog.artapi.support


import com.jarqprog.artapi.domain.art.ArtAggregate
import com.jarqprog.artapi.domain.art.ArtGenre
import com.jarqprog.artapi.domain.art.ArtStatus
import com.jarqprog.artapi.domain.vo.Author
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.domain.vo.User
import java.time.Instant

data class ComparableArt(
        val identifier: Identifier,
        val version: Int,
        val timestamp: Instant,
        val author: Author,
        val resource: Resource,
        val addedBy: User,
        val genre: ArtGenre,
        val status: ArtStatus
) {
    fun isEqualTo(art: ArtAggregate): Boolean {
        if (identifier != art.identifier()) return false
        if (version != art.version()) return false
        if (timestamp != art.timestamp()) return false
        if (author != art.author()) return false
        if (resource != art.resource()) return false
        if (addedBy != art.addedBy()) return false
        if (genre != art.genre()) return false
        if (status != art.status()) return false
        return true
    }
}