package br.com.colognesi.jdatabaseexporter.processor.input.repository

import br.com.colognesi.jdatabaseexporter.utils.MockEntity
import org.junit.Before
import org.junit.Test

class PgInputDatasourceReactiveRepositoryTest : AbstractPostgresContainer() {

    private lateinit var repository: PgInputDatasourceReactiveRepository<MockEntity>

    @Before
    fun setup() {
        repository = PgInputDatasourceReactiveRepository(connection)
    }

    @Test
    fun `'read' should read all the data from database`() {
        repository.read().blockFirst()
    }

}