package com.jarqprog.artapi.support

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.Serializable

object SerializationHelper {

    fun toJson(serializable: Serializable): String = MAPPER.writeValueAsString(serializable)
    fun <T> fromJson(json: String, type: Class<T>): T = MAPPER.readValue(json, type)

    private val MAPPER = jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
}