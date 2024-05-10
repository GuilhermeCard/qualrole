package com.br.qualrole.mapper

import com.br.qualrole.controller.queue.request.QueueRequest
import com.br.qualrole.domain.entity.QueueEntity
import com.br.qualrole.dto.CompanyDTO
import com.br.qualrole.dto.QueueDTO

fun QueueEntity.toQueueDTO() = QueueDTO(
    id = this.id,
    presentPeople = this.presentPeople,
    maxCapacity = this.maxCapacity,
    company = this.company.toCompanyDTO(),
    updatedAt = this.datUpdate
)

fun QueueDTO.toQueueEntity() = QueueEntity(
    presentPeople = this.presentPeople,
    maxCapacity = this.maxCapacity,
    company = this.company.toCompanyEntity()
)

fun QueueRequest.toQueueDTO(companyDTO: CompanyDTO) = QueueDTO(
    presentPeople = this.presentPeople,
    maxCapacity = this.maxCapacity,
    company = companyDTO
)