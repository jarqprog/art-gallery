package com.jarqprog.artapi.domain.vo

data class User(val login: String = UNKNOWN) {

    companion object Const {
        const val UNKNOWN = "unknown"
    }
}