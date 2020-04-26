package com.jarqprog.artapi.write.domain

import java.time.LocalDateTime
import java.util.*

data class ArtModel(val uuid: UUID = UUID.randomUUID(),
                    val author: String = ANONYMOUS,
                    val path: String = UNDEFINED,
                    val comments: List<CommentModel> = emptyList(),
                    val date: LocalDateTime = LocalDateTime.now())