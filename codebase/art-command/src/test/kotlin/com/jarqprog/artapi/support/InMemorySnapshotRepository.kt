package com.jarqprog.artapi.support

import com.jarqprog.artapi.domain.art.ArtDescriptor
import com.jarqprog.artapi.ports.outgoing.eventstore.SnapshotRepository
import com.jarqprog.artapi.domain.vo.Identifier
import reactor.core.publisher.Mono
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemorySnapshotRepository(private val memory: ConcurrentHashMap<UUID, MutableList<ArtDescriptor>>) : SnapshotRepository {

    override fun save(art: ArtDescriptor): Mono<Void> {
        return Mono.fromRunnable {
            Optional.ofNullable(memory[art.artId])
                    .ifPresentOrElse(
                            { snapshots -> snapshots.add(art) },
                            { memory[art.artId] = mutableListOf(art) }
                    )
        }
    }

    override fun loadLatest(artId: Identifier): Mono<ArtDescriptor> {
        return Optional.ofNullable(memory[artId.uuid()])
                .filter { descriptors -> descriptors.isNotEmpty() }
                .map { descriptors ->
                    descriptors
                            .sortedWith(Comparator.comparing(ArtDescriptor::timestamp))
                            .last()
                }
                .map { descriptor -> Mono.just(descriptor) }
                .orElse(Mono.empty())
    }
}
