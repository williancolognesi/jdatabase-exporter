package br.com.colognesi.jdatabaseexporter.processor.input

import br.com.colognesi.jdatabaseexporter.entity.Exportable
import io.r2dbc.spi.Readable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

abstract class InputDatasourceReactive(protected val clazz: Class<out Exportable>) {

    abstract fun read(): Flux<out Exportable>

    abstract fun clear(): Mono<Boolean>

    abstract fun toEntity(row: Readable): Exportable
}