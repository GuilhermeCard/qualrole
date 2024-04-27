package com.br.qualrole.builder

import com.br.qualrole.controller.queue.request.QueueRequest
import com.br.qualrole.domain.entity.CompanyEntity
import com.br.qualrole.domain.entity.QueueEntity

object QueueBuilder {

    fun giveQueueEntity(companyEntity: CompanyEntity) = QueueEntity(
        presentPeople = 50L,
        maxCapacity = 100L,
        company = companyEntity
    )

    fun giveQueueRequest(id: Long? = null, companyId: Long) = QueueRequest(
        id = id,
        presentPeople = 50L,
        maxCapacity = 100L,
        companyId = companyId,
    )
}