package com.jarqprog.artapi.command.domain.vo

import com.jarqprog.artapi.UNKNOWN

data class Author(val name: String = UNKNOWN, val user: User = User(UNKNOWN))