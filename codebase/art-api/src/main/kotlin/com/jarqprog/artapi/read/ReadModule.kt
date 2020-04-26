package com.jarqprog.artapi.read

import com.jarqprog.artapi.read.database.*
import org.davidmoten.rx.jdbc.ConnectionProvider
import org.davidmoten.rx.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

private const val DB_ENV = "spring.datasource.url"

@Configuration
class ReadModule {

    @Bean
    fun readDatabase(@Autowired environment: Environment): Database {
        val connectionProvider = ConnectionProvider
                .from(environment.getRequiredProperty(DB_ENV)+"&stringtype=unspecified")
        return Database.fromBlocking(connectionProvider)
    }

    @Bean
    fun databasePlugin(@Autowired database: Database): ReadDatabaseFacade {
        val readRepository = CommentReadRepository(database)
        val cache = CommentCache()
        return DatabasePlugin(readRepository, cache)
    }
}