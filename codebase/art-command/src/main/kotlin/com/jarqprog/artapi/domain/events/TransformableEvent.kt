package com.jarqprog.artapi.domain.events

import com.jarqprog.artapi.domain.exceptions.EventProcessingFailure
import com.jarqprog.artapi.support.SerializationHelper.fromJson
import com.jarqprog.artapi.support.SerializationHelper.toJson
import java.io.Serializable

interface TransformableEvent: Serializable {

    fun asJson(): String = toJson(this)

    companion object {
        fun fromDescriptor(descriptor: EventDescriptor): ArtEvent {
            val payload = descriptor.payload
            return try {
                when (descriptor.name) {
                    ArtCreated::class.java.simpleName -> fromJson(payload, ArtCreated::class.java)
                    ResourceChanged::class.java.simpleName -> fromJson(payload, ResourceChanged::class.java)
                    else -> throw EventProcessingFailure("Not handled descriptor name ${descriptor.name}")
                }
            } catch (ex: Exception) {
                throw EventProcessingFailure("error on creating event from $descriptor", ex)
            }
        }
    }
}