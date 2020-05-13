package com.jarqprog.artapi.querydepricated.exceptions

class DatabaseFailure(throwable: Throwable) :
        RuntimeException("Error occurred on retrieving data.", throwable)