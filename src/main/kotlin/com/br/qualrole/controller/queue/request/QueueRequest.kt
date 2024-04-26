package com.br.qualrole.controller.queue.request

import java.time.LocalDateTime

data class QueueRequest(
    val id: Long? = null,
    val presentPeople: Long? = 0L,
    val maxCapacity: Long,
    val companyId: Long,
    val updatedAt: LocalDateTime? = null
)
