package com.jarqprog.artapi.command.artdomain.commands

import com.jarqprog.artapi.command.artdomain.vo.Identifier

abstract class ArtCommand(

        internal val artId: Identifier,
        private val version: Int

) {

    private val name: String = javaClass.simpleName

    fun name() = name
    fun artId() = artId
    fun version() = version

    override fun toString(): String {
        return "ArtCommand(artId=$artId, version=$version, name='$name')"
    }


}