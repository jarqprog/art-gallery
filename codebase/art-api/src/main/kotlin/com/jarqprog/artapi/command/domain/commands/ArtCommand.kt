package com.jarqprog.artapi.command.domain.commands

import java.time.Instant
import java.util.*

abstract class ArtCommand(

        private val artUuid: UUID,
        private val version: Int

) {

    private val name: String = javaClass.simpleName

    fun name() = name
    fun artUuid() = artUuid
    fun version() = version

    override fun toString(): String {
        return "ArtCommand(artUuid=$artUuid, version=$version, name='$name')"
    }


}