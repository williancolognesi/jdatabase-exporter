package br.com.colognesi.jdatabaseexporter.processor.input.repository

import br.com.colognesi.jdatabaseexporter.entity.Exportable
import br.com.colognesi.jdatabaseexporter.processor.input.InputDatasourceReactive
import io.r2dbc.postgresql.api.PostgresqlConnection
import io.r2dbc.spi.Readable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class PgInputDatasourceReactiveRepository(
    private val connection: Mono<PostgresqlConnection>,
    clazz: Class<out Exportable>
) : InputDatasourceReactive(clazz) {

    override fun read(): Flux<out Exportable> {
        return connection.flatMapMany { conn ->
            conn.createStatement("SELECT * FROM datasource_input").execute()
        }.flatMap { result ->
            result.map { row ->
                toEntity(row)
            }
        }
    }

    override fun clear(): Mono<Boolean> {
        TODO("Not yet implemented")
    }

    override fun toEntity(row: Readable): Exportable {
        val t = clazz.declaredFields.map { instanceFieldName ->
            instanceFieldName.type to row.get(instanceFieldName.name, Object::class.java)
        }
        return clazz.getDeclaredConstructor(constructorValues).newInstance(constructorValues)
    }

    companion object {
        private const val QUERY_DATA = ""
    }
}