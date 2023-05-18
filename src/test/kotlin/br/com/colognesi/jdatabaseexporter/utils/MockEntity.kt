package br.com.colognesi.jdatabaseexporter.utils

import br.com.colognesi.jdatabaseexporter.entity.Exportable
import java.time.OffsetDateTime

class MockEntity(
    val id: Long,
    val title: String?,
    val timestamp: OffsetDateTime,
    val latitude: Float,
    val longitude: Float,
    val enabled: Boolean
) : Exportable