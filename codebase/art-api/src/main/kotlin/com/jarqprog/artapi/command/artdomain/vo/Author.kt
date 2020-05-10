package com.jarqprog.artapi.command.artdomain.vo

import com.jarqprog.artapi.UNKNOWN

data class Author(val name: String = UNKNOWN, val user: User = User(UNKNOWN))