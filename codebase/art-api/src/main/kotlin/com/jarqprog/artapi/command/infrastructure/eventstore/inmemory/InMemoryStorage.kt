package com.jarqprog.artapi.command.infrastructure.eventstore.inmemory

import com.jarqprog.artapi.command.artdomain.events.ArtEvent
import com.jarqprog.artapi.command.artdomain.vo.Identifier
import com.jarqprog.artapi.command.infrastructure.eventstore.Database
import com.jarqprog.artapi.command.infrastructure.eventstore.entity.EventStream

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryStorage(private val memory: ConcurrentHashMap<Identifier, EventStream>) : Database {

    override fun existsById(artId: Identifier): Boolean {
        return memory.contains(artId)
    }

    override fun streamVersion(artEvent: ArtEvent): Optional<Int> {
        return Optional.ofNullable(memory[artEvent.artId()])
                .map(EventStream::version)
    }

    override fun save(eventStream: EventStream) {
        val identifier = Identifier(eventStream.artId)
        memory[identifier] = eventStream
    }

    override fun load(artId: Identifier): Optional<EventStream> = Optional.ofNullable(memory[artId])

}
