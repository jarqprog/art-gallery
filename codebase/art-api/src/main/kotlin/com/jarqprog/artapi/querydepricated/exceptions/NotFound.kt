package com.jarqprog.artapi.querydepricated.exceptions

import java.util.*

class NotFound(uuid: UUID) : RuntimeException("Not found by uuid:${uuid}")