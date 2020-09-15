package com.jarqprog.artapi.support

import com.jarqprog.artapi.domain.art.ArtGenre
import com.jarqprog.artapi.domain.art.ArtStatus
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.events.ResourceChanged
import com.jarqprog.artapi.domain.vo.Author
import com.jarqprog.artapi.domain.vo.Identifier
import com.jarqprog.artapi.domain.vo.Resource
import com.jarqprog.artapi.domain.vo.User
import java.time.Instant

object EventContainer {

        val NOT_EXISTING_IDENTIFIER: Identifier = Identifier("2aa41ef2-11cc-111f-aa7a-98158b9435aa")
        val ANY_IDENTIFIER: Identifier = Identifier("2aa41ef2-11cc-427f-aa7a-98158b9435fd")
        val ANY_OTHER_IDENTIFIER: Identifier = Identifier("2aa41ef2-11cc-427f-aa7a-98158b94356a")
        val USER_MARIA: User = User("maria_maria")
        val AUTHOR_MARIA: Author = Author("Maria Gonzales", USER_MARIA)
        val ANY_RESOURCE = Resource("anyResourceUrl")
        private val ANY_RESOURCE_WITH_LONG_PATH = Resource("anyResourceUrlanyResourceUrlanyResourceUrl")
        private val ANY_OTHER_RESOURCE = Resource("anyChangedResourceUrl")
        private val FIRST_EVENT_CREATION_TIME = Instant.now().minusSeconds(100)

        val EVENT_ART_CREATED = ArtCreated(
                ANY_IDENTIFIER,
        0,
                FIRST_EVENT_CREATION_TIME,
                AUTHOR_MARIA,
                ANY_RESOURCE,
                USER_MARIA,
                ArtGenre.UNDEFINED,
                ArtStatus.ACTIVE
        )

        val EVENT_RESOURCE_URL_CHANGED_V1 = ResourceChanged(
                ANY_IDENTIFIER,
                1,
                FIRST_EVENT_CREATION_TIME.plusSeconds(10),
                ANY_RESOURCE_WITH_LONG_PATH
        )

        val EVENT_RESOURCE_URL_CHANGED_V2 = ResourceChanged(
                ANY_IDENTIFIER,
                2,
                FIRST_EVENT_CREATION_TIME.plusSeconds(20),
                ANY_OTHER_RESOURCE
        )

        val EVENT_RESOURCE_URL_CHANGED_V3 = ResourceChanged(
                ANY_IDENTIFIER,
                3,
                FIRST_EVENT_CREATION_TIME.plusSeconds(30),
                ANY_RESOURCE
        )

        val EVENT_RESOURCE_URL_CHANGED_V4 = ResourceChanged(
                ANY_IDENTIFIER,
                4,
                FIRST_EVENT_CREATION_TIME.plusSeconds(40),
                ANY_RESOURCE_WITH_LONG_PATH
        )

        val EVENT_RESOURCE_URL_CHANGED_V5 = ResourceChanged(
                ANY_IDENTIFIER,
                5,
                FIRST_EVENT_CREATION_TIME.plusSeconds(50),
                ANY_OTHER_RESOURCE
        )

        val ANOTHER_EVENT_ART_CREATED =  ArtCreated(
                ANY_OTHER_IDENTIFIER,
                0,
                FIRST_EVENT_CREATION_TIME,
                AUTHOR_MARIA,
                ANY_RESOURCE,
                USER_MARIA,
                ArtGenre.UNDEFINED,
                ArtStatus.ACTIVE
        )

        val ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1 = ResourceChanged(
                ANY_OTHER_IDENTIFIER,
                1,
                FIRST_EVENT_CREATION_TIME.plusSeconds(10),
                ANY_OTHER_RESOURCE
        )

        val ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2 = ResourceChanged(
                ANY_OTHER_IDENTIFIER,
                2,
                FIRST_EVENT_CREATION_TIME.plusSeconds(20),
                ANY_RESOURCE_WITH_LONG_PATH
        )

        val ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3 = ResourceChanged(
                ANY_OTHER_IDENTIFIER,
                3,
                FIRST_EVENT_CREATION_TIME.plusSeconds(30),
                ANY_OTHER_RESOURCE
        )

        val ONE_EVENT_LIST = listOf(EVENT_ART_CREATED)

        val TWO_EVENT_LIST = listOf(EVENT_ART_CREATED, EVENT_RESOURCE_URL_CHANGED_V1)

        val THREE_EVENTS_LIST = listOf(EVENT_ART_CREATED, EVENT_RESOURCE_URL_CHANGED_V1, EVENT_RESOURCE_URL_CHANGED_V2)

        val EVENTS_V_3_4_5 = listOf(
                EVENT_RESOURCE_URL_CHANGED_V3, EVENT_RESOURCE_URL_CHANGED_V4, EVENT_RESOURCE_URL_CHANGED_V5
        )

        val SIX_EVENTS_LIST = THREE_EVENTS_LIST.plus(EVENTS_V_3_4_5)
}
