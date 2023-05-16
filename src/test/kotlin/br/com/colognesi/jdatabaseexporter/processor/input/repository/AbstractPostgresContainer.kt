package br.com.colognesi.jdatabaseexporter.processor.input.repository

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.api.PostgresqlConnection
import io.r2dbc.spi.Connection
import org.junit.Rule
import org.testcontainers.containers.PostgreSQLContainer
import reactor.core.publisher.Mono

abstract class AbstractPostgresContainer {

    @JvmField
    @Rule
    val postgresContainer = PostgreSQLContainer(IMAGE_NAME)
        .withReuse(true)
        .withNetworkAliases("r2dbc-postgres")
        .withUsername("test")
        .withPassword("test")
        .withDatabaseName("jdatabase_exporter")
        .withExposedPorts(5432)

    protected var connection: Mono<PostgresqlConnection>

    init {
        postgresContainer.start()
        connection = PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder().host(postgresContainer.host)
                .username(postgresContainer.username).password(postgresContainer.password)
                .database(postgresContainer.databaseName).port(postgresContainer.firstMappedPort).build()
        ).create()
    }

    companion object {
        private const val IMAGE_NAME = "postgres:15.3-alpine3.18"
    }
}