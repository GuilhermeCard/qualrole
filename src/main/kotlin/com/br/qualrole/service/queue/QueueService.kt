package com.br.qualrole.service.queue

import com.br.qualrole.controller.queue.request.QueueRequest
import com.br.qualrole.controller.queue.request.UpdateSeatsRequest
import com.br.qualrole.domain.repository.QueueRepository
import com.br.qualrole.dto.QueueDTO
import com.br.qualrole.exception.ResourceAlreadyExists
import com.br.qualrole.exception.ResourceNotFoundException
import com.br.qualrole.mapper.QueueMapper
import com.br.qualrole.service.company.CompanyService
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class QueueService(
    val mapper: QueueMapper,
    val companyService: CompanyService,
    val repository: QueueRepository
) {

    fun create(request: QueueRequest): QueueDTO {
        verifyQueueAlreadyExists(request)
        val company = companyService.getCompanyById(request.companyId)
        val companyDTO = mapper.queueRequestToDTO(request).apply { this.company = company }

        return repository.save(mapper.queueDTOToEntity(companyDTO)).let { mapper.queueEntityToDTO(it) }
    }

    fun updateSeats(queueId: Long, request: UpdateSeatsRequest): QueueDTO {
        val queue = repository.findById(queueId).getOrNull()
            ?: throw ResourceNotFoundException("Queue not found with id: $queueId")

        request.maxCapacity?.let { queue.maxCapacity = it }
        queue.presentPeople = request.presentPeople
        return repository.save(queue).let { mapper.queueEntityToDTO(it) }
    }

    private fun verifyQueueAlreadyExists(request: QueueRequest) {
        request.id
            ?.let { repository.findById(it).getOrNull() }
            ?.let { throw ResourceAlreadyExists("Queue already exists with id: ${it.id}") }

        repository.findByCompanyId(request.companyId)
            ?.let { throw ResourceAlreadyExists("Queue already exists with companyId: ${it.id}") }
    }

    fun getAllQueues() = repository.findAll().map { mapper.queueEntityToDTO(it) }
}