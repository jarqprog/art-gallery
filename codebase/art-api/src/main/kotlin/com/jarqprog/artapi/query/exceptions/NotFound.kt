package com.jarqprog.artapi.query.exceptions

import java.util.*

class NotFound(uuid: UUID): RuntimeException("Not found by uuid:${uuid}")