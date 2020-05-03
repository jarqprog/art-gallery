package com.jarqprog.artapi.command.domain

import com.jarqprog.artapi.command.domain.commands.ArtCommand
import com.jarqprog.artapi.command.domain.commands.ChangeResourceUrl
import com.jarqprog.artapi.command.domain.commands.CreateArt
import com.jarqprog.artapi.command.domain.events.ArtCreated
import com.jarqprog.artapi.command.domain.events.ArtEvent
import com.jarqprog.artapi.command.domain.events.ResourceUrlChanged
import org.springframework.util.Assert
import java.time.Instant

class CommandValidationProcessor: CommandHandler {


    override fun handle(command: ArtCommand): ArtEvent = handle(command, emptyList())

//    @Test
//    @DisplayName("should throw exception when history unrelated with art uuid")
//    fun replayFromUnrelatedHistory() {
//        assertThrows<RuntimeException> { Art.replayAll(anyOtherUuid, threeEventsHistory) }
//
//        val unrelatedEvent = ResourceUrlChanged(anyOtherUuid, 3, timeNow.plusSeconds(2), anyResourceUrl)
//        assertThrows<RuntimeException> { Art.replayAll(anyUuid, threeEventsHistory.plus(unrelatedEvent)) }
//    }

    override fun handle(command: ArtCommand, history: List<ArtEvent>): ArtEvent {
        validateHistory(command, history)
        return when (command) {
            is CreateArt -> process(command)
            is ChangeResourceUrl -> process(command, history)
            else -> throw IllegalArgumentException("invalid unhandled command: $command")
        }
    }

    private fun process(command: CreateArt): ArtEvent {
        val initialState = Art.initialState(command.artUuid())
        validateCreateArt(command, initialState)
        val uuid = command.artUuid()
        return ArtCreated(
                uuid,
                command.version(),
                Instant.now(),
                command.author(),
                command.resourceUrl()
        )
    }

    private fun process(command: ChangeResourceUrl, events: List<ArtEvent>): ArtEvent {
        val uuid = command.artUuid()
        val currentState = Art.replayAll(uuid, events)
        validateChangeResourceUrl(command, currentState)
        return ResourceUrlChanged(
                uuid,
                command.version(),
                Instant.now(),
                command.newResourceUrl()
        )
    }


    companion object Validation {

        private fun validateHistory(command: ArtCommand, history: List<ArtEvent>) {
            when(command) {
                is CreateArt -> if (history.isNotEmpty())
                    throw IllegalArgumentException("error on processing $command - history is not empty: $history")
                else -> if (history.isEmpty())
                    throw IllegalArgumentException("error on processing $command - history is  empty")
            }
        }

        private fun validateCreateArt(command: CreateArt, initialState: Art) {
            validateArtUuidEquality(command, initialState)
            validateCommandVersionOnUpdate(command, initialState)
        }

        private fun validateChangeResourceUrl(command: ChangeResourceUrl, currentState: Art) {
            validateArtUuidEquality(command, currentState)
            validateCommandVersionOnUpdate(command, currentState)
            Assert.isTrue(command.newResourceUrl() != currentState.resourceUrl(),
                    "resource url is the same - event not created")

        }

        private fun validateArtUuidEquality(command: ArtCommand, currentState: Art) {
            val commandArtUuid = command.artUuid()
            val artUuid = currentState.uuid()
            Assert.isTrue(commandArtUuid == artUuid,
                    "invalid uuid, expected $artUuid but was: $commandArtUuid")
        }

        private fun validateCommandVersionOnUpdate(command: ArtCommand, currentState: Art) {
            val commandVersion = command.version()
            val expectedVersion = currentState.version().plus(1)
            Assert.isTrue(commandVersion == expectedVersion,
                    "invalid version, expected $expectedVersion but was: $commandVersion")
        }
    }
}