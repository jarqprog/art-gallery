package com.jarqprog.artapi.command.domain.commandprocessing

import arrow.core.getOrElse
import com.jarqprog.artapi.command.domain.*
import com.jarqprog.artapi.command.domain.commandhandling.ArtCommandValidatedProcessing
import com.jarqprog.artapi.command.domain.commandhandling.CommandValidator
import com.jarqprog.artapi.command.domain.commands.CreateArt
import com.jarqprog.artapi.command.domain.events.ArtCreated
import com.jarqprog.artapi.command.domain.exceptions.CommandProcessingFailure
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class ArtCreation {

    private val handler = ArtCommandValidatedProcessing(CommandValidator())

    private val command = CreateArt(
            EVENT_ART_CREATED.artUuid(),
            EVENT_ART_CREATED.author(),
            EVENT_ART_CREATED.resource(),
            EVENT_ART_CREATED.addedBy(),
            EVENT_ART_CREATED.artGenre(),
            EVENT_ART_CREATED.artStatus()
    )

    @Test
    @DisplayName("should create correct event")
    fun shouldCreateCorrectEvent() {

        val optionalEvent = handler.handle(command, HISTORY_WITHOUT_EVENTS).toOption()

        optionalEvent
                .map { event -> event as ArtCreated }
                .map { event -> assertArtCreatedEventsEquals(EVENT_ART_CREATED, event) }
                .getOrElse { fail() }
    }

    @Test
    @DisplayName("should create event without passing uuid and author")
    fun shouldCreateEventWithoutProvidingUuidAndAuthor() {

        val command = CreateArt(resource = EVENT_ART_CREATED.resource(), addedBy = EVENT_ART_CREATED.addedBy())
        val optionalEvent = handler.handle(command, HISTORY_WITHOUT_EVENTS).toOption()

        optionalEvent
                .map { event -> event as ArtCreated }
                .map { event ->
                    assertAll("should have proper values - provided or default",
                            { assertEquals(EVENT_ART_CREATED.eventName(), event.eventName()) },
                            { assertEquals(EVENT_ART_CREATED.version(), event.version()) },
                            { assertEquals(EVENT_ART_CREATED.resource(), event.resource()) },
                            { assertEquals(EVENT_ART_CREATED.addedBy(), event.addedBy()) },
                            { assertEquals(ArtGenre.UNDEFINED, event.artGenre()) },
                            { assertEquals(ArtStatus.ACTIVE, event.artStatus()) }
                    )
                }
                .getOrElse { fail() }
    }

    @Test
    @DisplayName("should return command processing failure when not empty history was passed")
    fun shouldReturnExceptionOnNotEmptyHistory() {

        val errorOrEvent = handler.handle(command, HISTORY_WITHOUT_EVENTS)

        errorOrEvent
                .mapLeft {
                    failure -> { assertEquals(CommandProcessingFailure::class.java, failure::class.java) }
                }
                .getOrElse { fail() }
    }
}