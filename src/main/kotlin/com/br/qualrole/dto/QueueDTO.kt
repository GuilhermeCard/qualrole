package com.br.qualrole.dto

import java.time.LocalDateTime

data class QueueDTO(
    val id: Long? = null,
    val presentPeople: Long,
    val maxCapacity: Long,
    var company: CompanyDTO,
    val updatedAt: LocalDateTime? = null
)
