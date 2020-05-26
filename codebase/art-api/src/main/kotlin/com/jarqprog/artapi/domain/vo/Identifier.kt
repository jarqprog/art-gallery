package com.jarqprog.artapi.domain.vo

import java.util.*


data class Identifier(val value: String) {

    companion object Factory {

        fun random() = Identifier(UUID.randomUUID().toString())
    }

}