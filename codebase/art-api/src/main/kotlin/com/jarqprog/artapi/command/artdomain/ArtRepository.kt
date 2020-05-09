package com.jarqprog.artapi.command.artdomain

import com.jarqprog.artapi.command.artdomain.vo.Identifier
import java.time.Instant


interface ArtRepository {

    fun save(aggregate: Art): Art
    fun getById(identifier: Identifier): Art
    fun getByUuidAt(identifier: Identifier, at: Instant): Art
}