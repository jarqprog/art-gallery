package com.jarqprog.artapi.applicationservice.dispatching

import com.jarqprog.artapi.domain.ArtHistory
import com.jarqprog.artapi.domain.ArtStatus
import com.jarqprog.artapi.applicationservice.validation.CommandValidator
import com.jarqprog.artapi.applicationservice.commands.CreateArt
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.applicationservice.exceptions.CommandProcessingFailure
import com.jarqprog.artapi.domain.ArtGenre
import com.jarqprog.artapi.support.CommandSupport.CREATE_ART
import com.jarqprog.artapi.support.EventAssertions.assertArtCreatedEventsEquals
import com.jarqprog.artapi.support.EventContainer.EVENT_ART_CREATED

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class ArtCreation {

    private val handler = CommandDispatcher(CommandValidator())

    @Test
    fun shouldCreateCorrectEvent() {

        val optionalEvent = handler.dispatch(CREATE_ART, ArtHistory.initialize(CREATE_ART.artId)).blockOptional()

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

        val shouldBeError = handler.dispatch(CREATE_ART, ArtHistory.withEvents(listOf(EVENT_ART_CREATED)))

        assertThrows<CommandProcessingFailure>("Should throw command processing failure") { shouldBeError.block() }
    }
}
