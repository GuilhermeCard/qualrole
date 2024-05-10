package com.br.qualrole.controller.queue.request

data class QueueRequest(
    val presentPeople: Long = 0L,
    val maxCapacity: Long,
    val companyId: Long
)
