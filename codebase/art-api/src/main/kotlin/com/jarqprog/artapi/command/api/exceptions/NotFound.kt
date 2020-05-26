package com.jarqprog.artapi.command.api.exceptions

import com.jarqprog.artapi.domain.vo.Identifier

class NotFound(artId: Identifier) : RuntimeException("Not found art by identifier: ${artId.value}")