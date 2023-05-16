package br.com.colognesi.jdatabaseexporter.utils

import br.com.colognesi.jdatabaseexporter.entity.Exportable
import java.time.OffsetDateTime

class MockEntity(
    val entityId: Long,
    val content: String,
    val datetime: OffsetDateTime
) : Exportable