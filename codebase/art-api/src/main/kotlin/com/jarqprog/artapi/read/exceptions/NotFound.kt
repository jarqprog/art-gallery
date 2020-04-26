package com.jarqprog.artapi.read.exceptions

import java.util.*

class NotFound(uuid: UUID): RuntimeException("Not found by uuid:${uuid}")