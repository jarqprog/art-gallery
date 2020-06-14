package com.jarqprog.artapi.domain.artfactory

import com.jarqprog.artapi.domain.ArtAggregate
import com.jarqprog.artapi.domain.ArtAggregate.Factory.INITIAL_VERSION
import com.jarqprog.artapi.domain.CommandSupport.ANY_IDENTIFIER
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Instant

internal class InitialState {

    @Test
    @DisplayName("initialized state should have given id")
    fun initializedStateIdentifier() {

        val initialState = ArtAggregate.initialState(ANY_IDENTIFIER)

        assertEquals(ANY_IDENTIFIER, initialState.identifier())
    }

    @Test
    @DisplayName("initialized state should have version -1")
    fun initializedStateVersion() {

        val expectedVersion = INITIAL_VERSION
        val initialState = ArtAggregate.initialState(ANY_IDENTIFIER)

        assertEquals(expectedVersion, initialState.version())
    }

    @Test
    @DisplayName("initialized state should have proper timestamp")
    fun initializedStateTimestamp() {

        val expectedTimestamp = Instant.MIN
        val initialState = ArtAggregate.initialState(ANY_IDENTIFIER)

        assertEquals(expectedTimestamp, initialState.timestamp())
    }
}