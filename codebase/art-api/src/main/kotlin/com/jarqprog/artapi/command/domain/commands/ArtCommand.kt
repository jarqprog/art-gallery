package com.jarqprog.artapi.command.domain.commands

import java.util.*

abstract class ArtCommand(

        private val artUuid: UUID,
        private val version: Int

) {

    fun artUuid(): UUID = artUuid
    fun version(): Int = version

    override fun toString(): String {
        return "ArtCommand(artUuid=$artUuid, version=$version)"
    }
}