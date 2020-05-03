package com.jarqprog.artapi.command.domain.processingcommands

import com.jarqprog.artapi.command.domain.*
import com.jarqprog.artapi.command.domain.commands.CreateArt
import com.jarqprog.artapi.command.domain.events.ArtCreated
import com.jarqprog.artapi.command.domain.events.ArtEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.lang.RuntimeException

internal class CreatingArt {

    private val handler = CommandValidationProcessor()
    private val emptyHistory = emptyList<ArtEvent>()

    private val command = CreateArt(
            EVENT_ART_CREATED.artUuid(),
            EVENT_ART_CREATED.author(),
            EVENT_ART_CREATED.resourceUrl()
    )

    @Test
    @DisplayName("should create correct event")
    fun shouldCreateCorrectEvent() {

        val event = handler.handle(command)

        assertTrue(event is ArtCreated)
        event as ArtCreated

        assertAll("createdEvent",
            { assertEquals(EVENT_ART_CREATED.artUuid(), event.artUuid()) },
            { assertEquals(0, event.version()) },
            { assertEquals(EVENT_ART_CREATED.author(), event.author()) },
            { assertEquals(EVENT_ART_CREATED.resourceUrl(), event.resourceUrl()) }
        )
    }

    @Test
    @DisplayName("should create event without passing uuid and author")
    fun shouldCreateEventWithoutProvidingUuidAndAuthor() {

        val command = CreateArt(resourceUrl = ANY_RESOURCE_URL)
        val event = handler.handle(command)

        assertTrue(event is ArtCreated)
        event as ArtCreated

        assertAll("created event is correct",
                { assertNotNull(event.artUuid()) },
                { assertEquals(0, event.version()) },
                { assertNotNull(event.author()) },
                { assertEquals(ANY_RESOURCE_URL, event.resourceUrl()) }
        )
    }

    @Test
    @DisplayName("should throw exception when not empty history was passed")
    fun shouldThrowExceptionOnNotEmptyHistory() {
        assertThrows<RuntimeException> { handler.handle(command, THREE_EVENTS_HISTORY) }
    }
}