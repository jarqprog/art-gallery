package com.jarqprog.artapi.ports.outgoing.eventstore.database

import com.jarqprog.artapi.ports.outgoing.eventstore.EventStreamRepository
import com.jarqprog.artapi.ports.outgoing.eventstore.SnapshotRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromStream
import reactor.core.publisher.Mono
import java.nio.file.Files
import java.nio.file.Paths

@Configuration
class DatabaseConfig(private val databaseClient: DatabaseClient) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val initSql = "db/V1_init.sql"

    @Bean
    fun eventStreamRepository(): EventStreamRepository {
        return EventStreamSql(databaseClient)
    }

    @Bean
    fun snapshotRepository(): SnapshotRepository {
        return SnapshotSql(databaseClient)
    }

    @Bean
    fun seeder(client: DatabaseClient): ApplicationRunner {
        return ApplicationRunner {
            sql()
                    .doFirst { logger.info("running sql initialization script") }
                    .flatMap { sql: String -> executeSql(client, sql) }
                    .subscribe { logger.info("sql script executed") }
        }
    }

    private fun executeSql(client: DatabaseClient, sql: String): Mono<Int> {
        return client.execute(sql).fetch().rowsUpdated()
    }

    private fun sql(): Mono<String> = Mono
            .fromCallable { Paths.get(ClassLoader.getSystemResource(initSql).toURI()) }
            .flatMap { path ->
                Flux
                        .using({ Files.lines(path) }, { stream -> fromStream(stream) }) { stream -> stream.close() }
                        .reduce { line, nextLine ->
                            """
                                $line
                                $nextLine
                            """.trimIndent()
                        }
            }
}