package com.jarqprog.artapi.applicationservice.dispatching

import com.jarqprog.artapi.applicationservice.ProcessingResult
import com.jarqprog.artapi.domain.art.ArtHistory
import com.jarqprog.artapi.domain.art.ArtStatus
import com.jarqprog.artapi.applicationservice.validation.CommandValidator
import com.jarqprog.artapi.applicationservice.commands.CreateArt
import com.jarqprog.artapi.domain.art.ArtAggregate
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.art.ArtGenre
import com.jarqprog.artapi.support.EventContainer
import com.jarqprog.artapi.support.EventContainer.EVENT_ART_CREATED
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class ArtCreation {

    private val handler = CommandDispatcher(CommandValidator())

    @Test
    fun `should create correct event`() {
        //given
        val command = CreateArt(
                EventContainer.AUTHOR_MARIA,
                EventContainer.ANY_RESOURCE,
                EventContainer.USER_MARIA,
                ArtGenre.UNDEFINED,
                ArtStatus.ACTIVE
        )

        val initializedHistory = ArtHistory.with(command.artId)

        val expectedEvent = ArtCreated(
                command.artId,
                command.version(),
                command.timestamp(),
                command.author(),
                command.resource(),
                command.addedBy(),
                command.artGenre(),
                command.artStatus()
        )
        val expectedSnapshot = ArtAggregate.replayAll(ArtHistory.with(command.artId, listOf(expectedEvent.asDescriptor())))
        val expectedResult = ProcessingResult(expectedEvent, expectedSnapshot)

        //when
        val optionalResult = handler.dispatch(command, initializedHistory).blockOptional()

        //then
        assertTrue(optionalResult.isPresent)
        optionalResult
                .map { result -> assertThat(result).isEqualTo(expectedResult) }
    }

    @Test
    fun `should create event without providing uuid and author`() {
        //given
        val command = CreateArt(resource = EVENT_ART_CREATED.resource(), addedBy = EVENT_ART_CREATED.addedBy())
        val initializedHistory = ArtHistory.with(command.artId)
        val expectedEvent = ArtCreated(
                command.artId,
                command.version(),
                command.timestamp(),
                command.author(),
                command.resource(),
                command.addedBy(),
                command.artGenre(),
                command.artStatus()
        )
        val expectedSnapshot = ArtAggregate.replayAll(ArtHistory.with(command.artId, listOf(expectedEvent.asDescriptor())))
        val expectedResult = ProcessingResult(expectedEvent, expectedSnapshot)

        //when
        val optionalResult = handler.dispatch(command, initializedHistory).blockOptional()

        //then
        assertTrue(optionalResult.isPresent)
        optionalResult
                .map { result -> assertThat(result).isEqualTo(expectedResult) }
    }
}
