package com.jarqprog.artapi.read.database

import java.util.*

interface LocalCache<K,V> {

    fun load(uuid: K): Optional<V>
    fun remember(uuid: K, optionalValue: Optional<V>)
}