package com.br.qualrole.controller.queue.request

class UpdateOperatingDataRequest(
    val presentPeople: Long,
    val maxCapacity: Long? = null,
    val isOpen: Boolean? = null
)
