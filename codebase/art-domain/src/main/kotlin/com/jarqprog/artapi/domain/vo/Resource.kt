package com.jarqprog.artapi.domain.vo

data class Resource(val path: String = UNDEFINED) {

    companion object Const {
        const val UNDEFINED = "undefined"
    }
}