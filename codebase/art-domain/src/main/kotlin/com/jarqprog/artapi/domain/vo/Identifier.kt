package com.jarqprog.artapi.domain.vo

import java.util.*


data class Identifier(val value: String) {

    fun uuid(): UUID = UUID.fromString(value)

    companion object Factory {

        fun random() = Identifier(UUID.randomUUID().toString())
    }

}