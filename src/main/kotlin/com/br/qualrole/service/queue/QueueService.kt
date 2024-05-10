package com.br.qualrole.service.queue

import com.br.qualrole.annotation.LogInfo
import com.br.qualrole.controller.queue.request.QueueFilterRequest
import com.br.qualrole.controller.queue.request.QueueRequest
import com.br.qualrole.controller.queue.request.UpdateSeatsRequest
import com.br.qualrole.domain.repository.QueueRepository
import com.br.qualrole.dto.QueueDTO
import com.br.qualrole.exception.ResourceAlreadyExistsException
import com.br.qualrole.exception.ResourceNotFoundException
import com.br.qualrole.mapper.toQueueDTO
import com.br.qualrole.mapper.toQueueEntity
import com.br.qualrole.service.company.CompanyService
import com.br.qualrole.service.queue.specification.QueueSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class QueueService(
    val companyService: CompanyService,
    val repository: QueueRepository
) {

    @LogInfo(logParameters = true)
    fun create(request: QueueRequest): QueueDTO {
        verifyQueueAlreadyExists(request)
        val companyDTO = companyService.getCompanyById(request.companyId)
        val queueDTO = request.toQueueDTO(companyDTO)

        return repository.save(queueDTO.toQueueEntity()).toQueueDTO()
    }

    @LogInfo(logParameters = true)
    fun updateSeats(queueId: Long, request: UpdateSeatsRequest): QueueDTO {
        val queue = repository.findById(queueId).getOrNull()
            ?: throw ResourceNotFoundException("Queue not found with id: $queueId")

        request.maxCapacity?.let { queue.maxCapacity = it }
        queue.presentPeople = request.presentPeople
        return repository.save(queue).toQueueDTO()
    }

    private fun verifyQueueAlreadyExists(request: QueueRequest) {
        repository.findByCompanyId(request.companyId)
            ?.let { throw ResourceAlreadyExistsException("Queue already exists with companyId: ${it.company.id}") }
    }

    @LogInfo(logParameters = true)
    fun getAllQueues(
        filterRequest: QueueFilterRequest,
        pageable: Pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "company.name")
    ): List<QueueDTO> =
        repository.findAll(QueueSpecification.searchWithSpecification(filterRequest), pageable)
            .map { it.toQueueDTO() }.content

}