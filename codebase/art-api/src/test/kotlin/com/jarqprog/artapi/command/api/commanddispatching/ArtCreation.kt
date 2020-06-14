package com.jarqprog.artapi.command.api.commanddispatching

import com.jarqprog.artapi.domain.EVENT_ART_CREATED
import com.jarqprog.artapi.command.api.commandvalidation.CommandValidator
import com.jarqprog.artapi.domain.*
import com.jarqprog.artapi.domain.commands.CreateArt
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.command.api.exceptions.CommandProcessingFailure
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class ArtCreation {

    private val handler = CommandDispatcher(CommandValidator())

    private val command = CreateArt(
            EVENT_ART_CREATED.artId(),
            EVENT_ART_CREATED.author(),
            EVENT_ART_CREATED.resource(),
            EVENT_ART_CREATED.addedBy(),
            EVENT_ART_CREATED.artGenre(),
            EVENT_ART_CREATED.artStatus()
    )

    @Test
    fun shouldCreateCorrectEvent() {

        val optionalEvent = handler.dispatch(command, ArtHistory.initialize(command.artId)).blockOptional()

        assertTrue(optionalEvent.isPresent)
        optionalEvent
                .map { event -> event as ArtCreated }
                .map { event -> assertArtCreatedEventsEquals(EVENT_ART_CREATED, event) }
    }

    @Test
    fun shouldCreateEventWithoutProvidingUuidAndAuthor() {

        val command = CreateArt(resource = EVENT_ART_CREATED.resource(), addedBy = EVENT_ART_CREATED.addedBy())
        val optionalEvent = handler.dispatch(command, ArtHistory.initialize(command.artId)).blockOptional()

        assertTrue(optionalEvent.isPresent)
        optionalEvent
                .map { event -> event as ArtCreated }
                .map { event ->
                    assertAll("should have proper values - provided or default",
                            { assertEquals(EVENT_ART_CREATED.name(), event.name()) },
                            { assertEquals(EVENT_ART_CREATED.version(), event.version()) },
                            { assertEquals(EVENT_ART_CREATED.resource(), event.resource()) },
                            { assertEquals(EVENT_ART_CREATED.addedBy(), event.addedBy()) },
                            { assertEquals(ArtGenre.UNDEFINED, event.artGenre()) },
                            { assertEquals(ArtStatus.ACTIVE, event.artStatus()) }
                    )
                }
    }

    @Test
    fun shouldReturnExceptionOnNotEmptyHistory() {

        val shouldBeError = handler.dispatch(command, ArtHistory.withEvents(listOf(EVENT_ART_CREATED)))

        assertThrows<CommandProcessingFailure>("Should throw command processing failure") { shouldBeError.block() }
    }
}