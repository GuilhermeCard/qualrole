package com.br.qualrole.service

import com.br.qualrole.controller.queue.request.QueueFilterRequest
import com.br.qualrole.controller.queue.request.QueueRequest
import com.br.qualrole.controller.queue.request.UpdateOperatingDataRequest
import com.br.qualrole.dto.QueueDTO
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface QueueService {
    fun create(request: QueueRequest): QueueDTO

    fun updateOperatingData(queueId: Long, request: UpdateOperatingDataRequest): QueueDTO

    fun getAllQueues(
        filterRequest: QueueFilterRequest,
        pageable: Pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "company.name")
    ): List<QueueDTO>
}