package com.jarqprog.artapi.applicationservice.exceptions

import com.jarqprog.artapi.domain.vo.Identifier

class NotFound(artId: Identifier) : RuntimeException("Not found art by identifier: ${artId.value}")