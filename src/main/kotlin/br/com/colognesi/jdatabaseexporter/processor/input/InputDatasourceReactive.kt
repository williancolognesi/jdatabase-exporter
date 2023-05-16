package br.com.colognesi.jdatabaseexporter.processor.input

import io.r2dbc.spi.Readable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface InputDatasourceReactive<out Exportable> {

    fun read(): Flux<out Exportable>

    fun clear(): Mono<Boolean>

    fun <T> toEntity(row: Readable): T
}