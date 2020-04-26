package com.jarqprog.artapi.read.exceptions

class DatabaseFailure(throwable: Throwable):
        RuntimeException("Error occurred on retrieving data.", throwable)