package com.br.qualrole.controller.queue.request

class UpdateSeatsRequest(
    val presentPeople: Long,
    val maxCapacity: Long? = null,
)
