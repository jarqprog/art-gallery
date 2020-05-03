package com.jarqprog.artapi.command.domain.artfactory

import com.jarqprog.artapi.command.domain.ANY_UUID
import com.jarqprog.artapi.command.domain.Art
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


internal class InitialState {

    @Test
    @DisplayName("initialized state should have given uuid")
    fun initializedStateUuid() {

        val initialState = Art.initialState(ANY_UUID)

        assertEquals(ANY_UUID, initialState.uuid())
    }

    @Test
    @DisplayName("initialized state should have version -1")
    fun initializedStateVersion() {

        val expectedVersion = -1
        val initialState = Art.initialState(ANY_UUID)

        assertEquals(expectedVersion, initialState.version())
    }

    @Test
    @DisplayName("initialized state should have empty history")
    fun initializedStateHistory() {

        val initialState = Art.initialState(ANY_UUID)

        assertTrue(initialState.history().isEmpty())
    }
}