package com.br.qualrole.mapper

import com.br.qualrole.controller.queue.request.QueueRequest
import com.br.qualrole.domain.entity.QueueEntity
import com.br.qualrole.dto.QueueDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface QueueMapper {

    @Mapping(source = "datUpdate", target = "updatedAt")
    fun queueEntityToDTO(queueEntity: QueueEntity): QueueDTO

    fun queueDTOToEntity(queueDTO: QueueDTO): QueueEntity

    fun queueRequestToDTO(request: QueueRequest): QueueDTO

}