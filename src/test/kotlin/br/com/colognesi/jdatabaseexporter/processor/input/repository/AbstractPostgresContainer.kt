package br.com.colognesi.jdatabaseexporter.processor.input.repository

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.api.PostgresqlConnection
import io.r2dbc.postgresql.api.PostgresqlResult
import org.junit.Rule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.PostgreSQLContainer
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

abstract class AbstractPostgresContainer(vararg migrations: String) {

    private val logger: Logger = LoggerFactory.getLogger(AbstractPostgresContainer::class.java)

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
        connection.block().also { conn ->
            Flux.fromArray(migrations)
                .flatMap { migrationFile ->
                    migrate(migrationFile, conn)
                }.blockLast()
        }
    }

    private fun migrate(fileName: String, connection: PostgresqlConnection): Flux<PostgresqlResult> {
        return javaClass.classLoader.getResourceAsStream(fileName)
            ?.bufferedReader()
            ?.readText()
            ?.let { query ->
                connection.createStatement(query).execute()
            } ?: Flux.empty()
    }

    companion object {
        private const val IMAGE_NAME = "postgres:15.3-alpine3.18"
    }
}