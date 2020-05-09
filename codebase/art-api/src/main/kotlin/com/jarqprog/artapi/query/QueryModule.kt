package com.jarqprog.artapi.query

import com.jarqprog.artapi.query.api.*
import com.jarqprog.artapi.query.storage.ArtCache
import com.jarqprog.artapi.query.storage.RxRepositoryWithCache
import org.davidmoten.rx.jdbc.ConnectionProvider
import org.davidmoten.rx.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

private const val DB_ENV = "spring.datasource.url"

@Configuration
class QueryModule {

    @Bean
    fun readDatabase(@Autowired environment: Environment): Database {
        val connectionProvider = ConnectionProvider
                .from(environment.getRequiredProperty(DB_ENV) + "&stringtype=unspecified")
        return Database.fromBlocking(connectionProvider)
    }

    @Bean
    fun queryRepository(@Autowired readDatabase: Database): QueryRepository {
        return RxRepositoryWithCache(readDatabase, ArtCache())
    }

    @Bean
    fun queryPlugin(@Autowired queryRepository: QueryRepository): QueryFacade {
        return QueryPlugin(queryRepository)
    }
}