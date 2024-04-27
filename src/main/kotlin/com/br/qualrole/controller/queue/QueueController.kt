package com.br.qualrole.controller.queue

import com.br.qualrole.controller.queue.request.QueueFilterRequest
import com.br.qualrole.controller.queue.request.QueueRequest
import com.br.qualrole.controller.queue.request.UpdateSeatsRequest
import com.br.qualrole.service.queue.QueueService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/queue")
class QueueController(val queueService: QueueService) {

    @PostMapping("/create")
    fun create(@RequestBody request: QueueRequest) = queueService.create(request)

    @PutMapping("/{queueId}")
    fun updateSeats(
        @PathVariable queueId: Long,
        @RequestBody request: UpdateSeatsRequest
    ) = queueService.updateSeats(queueId, request)

    @GetMapping
    fun retrieveQueues(filterRequest: QueueFilterRequest, pageable: Pageable) = queueService.getAllQueues(filterRequest, pageable)
}