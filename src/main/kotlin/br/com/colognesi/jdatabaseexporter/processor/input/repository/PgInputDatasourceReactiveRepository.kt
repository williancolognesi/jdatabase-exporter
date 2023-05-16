package br.com.colognesi.jdatabaseexporter.processor.input.repository

import br.com.colognesi.jdatabaseexporter.processor.input.InputDatasourceReactive
import io.r2dbc.postgresql.api.PostgresqlConnection
import io.r2dbc.spi.Readable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class PgInputDatasourceReactiveRepository<T>(private val connection: Mono<PostgresqlConnection>) : InputDatasourceReactive<T> {

    override fun read(): Flux<T> {
        connection.flatMapMany { conn ->
            conn.createStatement("SELECT * FROM pg_stat_activity")
                .execute()
        }.map { result ->
            result.map { row ->
                println(row.get("backend_start"))
            }
        }.blockFirst()
        return Flux.empty()
    }

    override fun clear(): Mono<Boolean> {
        TODO("Not yet implemented")
    }

    override fun <T> toEntity(row: Readable): T {
        TODO("Not yet implemented")
    }

    companion object {
        private const val QUERY_DATA = ""
    }
}