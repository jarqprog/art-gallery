package com.jarqprog.artapi.query.exceptions

class DatabaseFailure(throwable: Throwable):
        RuntimeException("Error occurred on retrieving data.", throwable)