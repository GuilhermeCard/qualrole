package com.br.qualrole.dto

import java.time.LocalDateTime

data class QueueDTO(
    val id: Long? = null,
    val presentPeople: Long? = 0L,
    val maxCapacity: Long,
    var company: CompanyDTO? = null,
    val updatedAt: LocalDateTime? = null
)
