package com.jarqprog.artapi.domain

import com.jarqprog.artapi.domain.CommandSupport.ANOTHER_CHANGE_RESOURCE_0
import com.jarqprog.artapi.domain.CommandSupport.ANOTHER_CHANGE_RESOURCE_1
import com.jarqprog.artapi.domain.CommandSupport.ANOTHER_CHANGE_RESOURCE_2
import com.jarqprog.artapi.domain.CommandSupport.ANOTHER_CREATE_ART
import com.jarqprog.artapi.domain.CommandSupport.CHANGE_RESOURCE_0
import com.jarqprog.artapi.domain.CommandSupport.CHANGE_RESOURCE_1
import com.jarqprog.artapi.domain.CommandSupport.CHANGE_RESOURCE_2
import com.jarqprog.artapi.domain.CommandSupport.CHANGE_RESOURCE_3
import com.jarqprog.artapi.domain.CommandSupport.CHANGE_RESOURCE_4
import com.jarqprog.artapi.domain.CommandSupport.CREATE_ART
import com.jarqprog.artapi.domain.events.ArtCreated
import com.jarqprog.artapi.domain.events.ResourceChanged

object EventSupport {

        val EVENT_ART_CREATED = ArtCreated.from(CREATE_ART)

        val EVENT_RESOURCE_URL_CHANGED_V1 = ResourceChanged.from(CHANGE_RESOURCE_0)

        val EVENT_RESOURCE_URL_CHANGED_V2 = ResourceChanged.from(CHANGE_RESOURCE_1)

        val EVENT_RESOURCE_URL_CHANGED_V3 = ResourceChanged.from(CHANGE_RESOURCE_2)

        val EVENT_RESOURCE_URL_CHANGED_V4 = ResourceChanged.from(CHANGE_RESOURCE_3)

        val EVENT_RESOURCE_URL_CHANGED_V5 = ResourceChanged.from(CHANGE_RESOURCE_4)

        val ANOTHER_EVENT_ART_CREATED =  ArtCreated.from(ANOTHER_CREATE_ART)

        val ANOTHER_EVENT_RESOURCE_URL_CHANGED_V1 = ResourceChanged.from(ANOTHER_CHANGE_RESOURCE_0)

        val ANOTHER_EVENT_RESOURCE_URL_CHANGED_V2 = ResourceChanged.from(ANOTHER_CHANGE_RESOURCE_1)

        val ANOTHER_EVENT_RESOURCE_URL_CHANGED_V3 = ResourceChanged.from(ANOTHER_CHANGE_RESOURCE_2)

        val ONE_EVENT_LIST = listOf(EVENT_ART_CREATED)

        val TWO_EVENT_LIST =  listOf(EVENT_ART_CREATED, EVENT_RESOURCE_URL_CHANGED_V1)

        val THREE_EVENTS_LIST =  listOf(EVENT_ART_CREATED, EVENT_RESOURCE_URL_CHANGED_V1, EVENT_RESOURCE_URL_CHANGED_V2)


        val EVENTS_V_3_4_5 = listOf(
                EVENT_RESOURCE_URL_CHANGED_V3, EVENT_RESOURCE_URL_CHANGED_V4, EVENT_RESOURCE_URL_CHANGED_V5
        )

}