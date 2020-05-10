package com.jarqprog.artapi.command.artdomain.artfactory

import com.jarqprog.artapi.command.ANY_IDENTIFIER
import com.jarqprog.artapi.command.artdomain.Art
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class InitialState {

    @Test
    @DisplayName("initialized state should have given uuid")
    fun initializedStateUuid() {

        val initialState = Art.initialState(ANY_IDENTIFIER)

        assertEquals(ANY_IDENTIFIER, initialState.identifier())
        assertEquals(ANY_IDENTIFIER, initialState.identifier())
    }

    @Test
    @DisplayName("initialized state should have version -1")
    fun initializedStateVersion() {

        val expectedVersion = -1
        val initialState = Art.initialState(ANY_IDENTIFIER)

        assertEquals(expectedVersion, initialState.version())
        assertEquals(ANY_IDENTIFIER, initialState.identifier())
    }

    @Test
    @DisplayName("initialized state should have empty history")
    fun initializedStateHistory() {

        val initialState = Art.initialState(ANY_IDENTIFIER)

        assertTrue(initialState.history().isEmpty())
    }
}