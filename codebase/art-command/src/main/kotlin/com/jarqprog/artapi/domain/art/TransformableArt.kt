package com.jarqprog.artapi.domain.art

import com.jarqprog.artapi.support.SerializationHelper
import com.jarqprog.artapi.support.SerializationHelper.fromJson

import java.io.Serializable

interface TransformableArt: Serializable {

    fun asJson(): String = SerializationHelper.toJson(this)

    companion object {
        fun fromDescriptor(descriptor: ArtDescriptor): ArtAggregate = fromJson(descriptor.payload, ArtAggregate::class.java)
    }
}