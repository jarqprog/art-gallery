package com.jarqprog.artapi.command.domain.events

import com.jarqprog.artapi.command.domain.vo.Identifier
import java.time.Instant


abstract class ArtEvent(

        private val artId: Identifier,
        private val version: Int,
        private val timestamp: Instant

) {
    private val eventType: String = javaClass.superclass.simpleName
    private val eventName: String = javaClass.simpleName

    fun eventType() = eventType
    fun eventName() = eventName
    fun artId(): Identifier = artId
    fun version(): Int = version
    fun timestamp(): Instant = timestamp


    override fun toString(): String {
        return "ArtEvent(artId=$artId, version=$version, timestamp=$timestamp, eventType='$eventType', eventName='$eventName')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArtEvent) return false

        if (artId != other.artId) return false
        if (version != other.version) return false
        if (timestamp != other.timestamp) return false
        if (eventType != other.eventType) return false
        if (eventName != other.eventName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = artId.hashCode()
        result = 31 * result + version
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + eventType.hashCode()
        result = 31 * result + eventName.hashCode()
        return result
    }
}