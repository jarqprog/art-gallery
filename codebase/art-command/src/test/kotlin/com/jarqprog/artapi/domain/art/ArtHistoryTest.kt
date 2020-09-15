package com.jarqprog.artapi.domain.art

import com.jarqprog.artapi.domain.art.ArtTestUtils.eventsToDescriptors
import com.jarqprog.artapi.domain.events.ArtEvent
import com.jarqprog.artapi.support.EventContainer.ANY_IDENTIFIER
import com.jarqprog.artapi.support.EventContainer.EVENTS_V_3_4_5
import com.jarqprog.artapi.support.EventContainer.ONE_EVENT_LIST
import com.jarqprog.artapi.support.EventContainer.THREE_EVENTS_LIST
import com.jarqprog.artapi.support.SnapshotContainer.SNAPSHOT_V2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.lang.RuntimeException
import java.time.Instant

internal class ArtHistoryTest {

    @Test
    fun `should initialize history with art identifier`() {
        //given
        val expectedVersion = -1
        val expectedTimestamp = Instant.MIN

        //when
        val history = ArtHistory.with(ANY_IDENTIFIER)

        //then
        assertThat(history.artId()).isEqualTo(ANY_IDENTIFIER)
        assertThat(history.version()).isEqualTo(expectedVersion)
        assertThat(history.timestamp()).isEqualTo(expectedTimestamp)
        assertThat(history.isEmpty()).isTrue()
        assertThat(history.snapshot()).isEqualTo(ArtAggregate.initialState(ANY_IDENTIFIER))
    }

    @Test
    fun `should create history with version -1 when no events provided`() {
        //given
        val expectedVersion = -1
        val expectedTimestamp = Instant.MIN

        //when
        val history = ArtHistory.with(ANY_IDENTIFIER, listOf())

        //then
        assertThat(history.artId()).isEqualTo(ANY_IDENTIFIER)
        assertThat(history.version()).isEqualTo(expectedVersion)
        assertThat(history.timestamp()).isEqualTo(expectedTimestamp)
        assertThat(history.isEmpty()).isTrue()
        assertThat(history.snapshot()).isEqualTo(ArtAggregate.initialState(ANY_IDENTIFIER))
    }

    @Test
    fun `should return snapshot equals to initial state when no snapshot provided on creating history`() {
        //when
        val history = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(ONE_EVENT_LIST))

        //then
        assertThat(history.snapshot()).isEqualTo(ArtAggregate.initialState(ANY_IDENTIFIER))
    }

    @Test
    fun `should create history with one event`() {
        //when
        val history = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(ONE_EVENT_LIST))

        //then
        assertThatHistoryAndEventsValuesMatch(history, ONE_EVENT_LIST)
    }

    @Test
    fun `should create history with three events`() {
        //when
        val history = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(THREE_EVENTS_LIST))

        //then
        assertThatHistoryAndEventsValuesMatch(history, THREE_EVENTS_LIST)
    }

    @Test
    fun `should create history when events provided in reversed order`() {
        //given
        val eventsInReversedOrder = THREE_EVENTS_LIST.sortedByDescending(ArtEvent::timestamp)

        //when
        val history = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(eventsInReversedOrder))

        //then
        assertThatHistoryAndEventsValuesMatch(history, eventsInReversedOrder)
    }

    @Test
    fun `should create history with snapshot and no events`() {
        //when
        val history = ArtHistory.with(ANY_IDENTIFIER, listOf(), SNAPSHOT_V2.asDescriptor())

        //then
        assertThat(history.snapshot()).isEqualTo(SNAPSHOT_V2)
        assertThat(history.isEmpty()).isTrue()
        assertThatHistoryAndArtValuesMatch(history, SNAPSHOT_V2)
    }

    @Test
    fun `should create history with snapshot and events`() {
        //when
        val history = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(EVENTS_V_3_4_5), SNAPSHOT_V2.asDescriptor())

        //then
        assertThat(history.snapshot()).isEqualTo(SNAPSHOT_V2)
        assertThat(history.events().size).isEqualTo(EVENTS_V_3_4_5.size)
        assertThatHistoryAndEventsValuesMatch(history, EVENTS_V_3_4_5)
    }

    @Test
    fun `should ignore older events than snapshot when creating history`() {
        //given
        val snapshot = SNAPSHOT_V2
        val eventsToBeUsed = EVENTS_V_3_4_5
        val eventsToBeIgnored = THREE_EVENTS_LIST
        val allEvents = eventsToBeUsed.plus(eventsToBeIgnored)

        //when
        val history = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(allEvents), snapshot.asDescriptor())

        //then
        assertThat(history.snapshot()).isEqualTo(snapshot)
        assertThat(history.events().size).isEqualTo(eventsToBeUsed.size)
        assertThatHistoryAndEventsValuesMatch(history, eventsToBeUsed)

        eventsToBeIgnored.forEach {
            assertThat(history.events().contains(it)).isFalse()
        }
    }

    @Test
    fun `should create history from art descriptors`() {
        //when
        val history = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(THREE_EVENTS_LIST))

        //then
        assertThat(history.events().size).isEqualTo(THREE_EVENTS_LIST.size)
        assertThatHistoryAndEventsValuesMatch(history, THREE_EVENTS_LIST)
        assertThat(history.snapshot()).isEqualTo(ArtAggregate.initialState(ANY_IDENTIFIER))
    }

    @Test
    fun `should create history from event descriptors and art descriptor`() {
        //given
        val artSnapshot = SNAPSHOT_V2
        val eventsToBeUsed = EVENTS_V_3_4_5
        val eventsToBeIgnored = THREE_EVENTS_LIST
        val allEvents = eventsToBeUsed.plus(eventsToBeIgnored)

        //when
        val history = ArtHistory.with(ANY_IDENTIFIER, eventsToDescriptors(allEvents), artSnapshot.asDescriptor())

        //then
        assertThat(history.snapshot()).isEqualTo(artSnapshot)
        assertThat(history.events().size).isEqualTo(eventsToBeUsed.size)
        assertThatHistoryAndEventsValuesMatch(history, eventsToBeUsed)

        eventsToBeIgnored.forEach {
            assertThat(history.events().contains(it)).isFalse()
        }
    }

    @Test
    fun `should create history from event descriptors by given point in time`() {
        //given
        val newerEvents = EVENTS_V_3_4_5
        val olderEvents = THREE_EVENTS_LIST
        val pointInTime = newerEvents.minBy(ArtEvent::timestamp)!!.timestamp().minusSeconds(1)
        val allEvents = newerEvents.plus(olderEvents)

        //when
        val history = ArtHistory.from(ANY_IDENTIFIER, eventsToDescriptors(allEvents), pointInTime)

        //then
        assertThat(history.events().size).isEqualTo(olderEvents.size)
        assertThatHistoryAndEventsValuesMatch(history, olderEvents)

        newerEvents.forEach {
            assertThat(history.events().contains(it)).isFalse()
        }
    }

    @Test
    fun `should create history with version -1 when point in time is earlier than first event`() {
        //given
        val events = THREE_EVENTS_LIST
        val pointInTime = THREE_EVENTS_LIST.minBy(ArtEvent::timestamp)!!.timestamp().minusSeconds(100)

        //when
        val history = ArtHistory.from(ANY_IDENTIFIER, eventsToDescriptors(events), pointInTime)

        //then
        assertThat(history.isEmpty()).isTrue()
        assertThat(history).isEqualTo(ArtHistory.with(ANY_IDENTIFIER))
    }

    @Test
    fun `should create history with all the events when point in time is later than latest event`() {
        //given
        val events = THREE_EVENTS_LIST
        val pointInTime = THREE_EVENTS_LIST.maxBy(ArtEvent::timestamp)!!.timestamp().plusSeconds(100)

        //when
        val history = ArtHistory.from(ANY_IDENTIFIER, eventsToDescriptors(events), pointInTime)

        //then
        assertThat(history.events().size).isEqualTo(events.size)
        assertThatHistoryAndEventsValuesMatch(history, events)
    }

    private companion object {
        private fun assertThatHistoryAndEventsValuesMatch(history: ArtHistory, events: List<ArtEvent>) {
            if (events.isEmpty()) throw RuntimeException("Cannot compare to empty events list")
            val sorted = events.sortedBy(ArtEvent::timestamp)
            val latestEvent = sorted.last()
            assertThat(history.artId()).isEqualTo(latestEvent.artId())
            assertThat(history.version()).isEqualTo(latestEvent.version())
            assertThat(history.timestamp()).isEqualTo(latestEvent.timestamp())
            assertThat(history.events().size).isEqualTo(events.size)
            assertThat(history.events().last()).isEqualTo(latestEvent)
        }

        private fun assertThatHistoryAndArtValuesMatch(history: ArtHistory, art: ArtAggregate) {
            assertThat(history.artId()).isEqualTo(art.identifier())
            assertThat(history.version()).isEqualTo(art.version())
            assertThat(history.timestamp()).isEqualTo(art.timestamp())
        }
    }
}
