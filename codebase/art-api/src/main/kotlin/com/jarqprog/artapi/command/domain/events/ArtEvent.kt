package com.jarqprog.artapi.command.domain.events

import java.time.Instant
import java.util.*


abstract class ArtEvent(

        private val artUuid: UUID,
        private val version: Int,
        private val timestamp: Instant

) {

    private val eventType: String = javaClass.superclass.simpleName
    private val eventName: String = javaClass.simpleName

    fun eventType() = eventType
    fun eventName(): String = eventName
    fun artUuid(): UUID = artUuid
    fun version(): Int = version
    fun timestamp(): Instant = timestamp



}