package br.com.colognesi.jdatabaseexporter.processor.input.repository

import br.com.colognesi.jdatabaseexporter.processor.input.InputDatasourceReactive
import br.com.colognesi.jdatabaseexporter.utils.MockEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class PgInputDatasourceReactiveRepositoryTest : AbstractPostgresContainer("datasource-input.sql") {

    private lateinit var repository: InputDatasourceReactive

    @Before
    fun setup() {
        repository = PgInputDatasourceReactiveRepository(connection, MockEntity::class.java)
    }

    @Test
    fun `'read' should read all the data from database`() {
        val result = repository.read().collectList().block()
        assertThat(result).hasSize(3)
    }

}