package com.jarqprog.artapi.command.domain.artfactory

import com.jarqprog.artapi.command.domain.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


internal class ReplayingHistory {

    @Test
    @DisplayName("replay state from history with one event")
    fun replayHistoryWithOneEvent() {

        val expected = Art(
                EVENT_ART_CREATED.artUuid(),
                EVENT_ART_CREATED.version(),
                EVENT_ART_CREATED.author(),
                EVENT_ART_CREATED.resourceUrl(),
                ONE_EVENT_HISTORY
        )

        val replayed = Art.replayAll(ANY_UUID, ONE_EVENT_HISTORY)

        assertStatesEquals(expected, replayed)
    }

    @Test
    @DisplayName("replay state from history with two events")
    fun replayHistoryWithTwoEvent() {
        val expected = Art(
                EVENT_RESOURCE_URL_CHANGED_V1.artUuid(),
                EVENT_RESOURCE_URL_CHANGED_V1.version(),
                EVENT_ART_CREATED.author(),
                EVENT_RESOURCE_URL_CHANGED_V1.newResourceUrl(),
                TWO_EVENTS_HISTORY
        )

        val replayed = Art.replayAll(ANY_UUID, TWO_EVENTS_HISTORY)

        assertStatesEquals(expected, replayed)
    }

    @Test
    @DisplayName("replay state from history with three events")
    fun replayHistoryWithThreeEvents() {
        val expected = Art(
                EVENT_RESOURCE_URL_CHANGED_V2.artUuid(),
                EVENT_RESOURCE_URL_CHANGED_V2.version(),
                EVENT_ART_CREATED.author(),
                EVENT_RESOURCE_URL_CHANGED_V2.newResourceUrl(),
                THREE_EVENTS_HISTORY
        )

        val replayed = Art.replayAll(ANY_UUID, THREE_EVENTS_HISTORY)

        assertStatesEquals(expected, replayed)
    }

    @Test
    @DisplayName("replay state from history in reversed order")
    fun replayReversedHistory() {
        val expected = Art(
                EVENT_RESOURCE_URL_CHANGED_V2.artUuid(),
                EVENT_RESOURCE_URL_CHANGED_V2.version(),
                EVENT_ART_CREATED.author(),
                EVENT_RESOURCE_URL_CHANGED_V2.newResourceUrl(),
                THREE_EVENTS_HISTORY
        )

        val replayed = Art.replayAll(ANY_UUID, THREE_EVENTS_HISTORY.reversed())

        assertStatesEquals(expected, replayed)
    }
}