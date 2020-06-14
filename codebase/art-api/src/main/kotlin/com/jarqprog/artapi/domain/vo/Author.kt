package com.jarqprog.artapi.domain.vo

data class Author(val name: String = UNKNOWN, val user: User = User()) {

    companion object Const {
        const val UNKNOWN = "unknown"
    }
}