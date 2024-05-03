package com.br.qualrole.service.address

import com.br.qualrole.annotation.LogInfo
import com.br.qualrole.controller.address.request.AddressFilterRequest
import com.br.qualrole.domain.repository.AddressRepository
import com.br.qualrole.dto.AddressDTO
import com.br.qualrole.exception.ResourceNotFoundException
import com.br.qualrole.mapper.AddressMapper
import com.br.qualrole.service.address.specification.AddressSpecification
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class AddressService(
    val mapper: AddressMapper,
    val addressRepository: AddressRepository
) {

    @LogInfo(logParameters = true)
    fun create(body: AddressDTO) = mapper.addressDTOtoEntity(body)
        .let { addressRepository.save(it) }
        .let { mapper.addressEntityToDTO(it) }

    @LogInfo(logParameters = true)
    fun getByFilterSpecification(
        filter: AddressFilterRequest,
        pageable: Pageable
    ): List<AddressDTO> {
        val specification = AddressSpecification.searchWithSpecification(filter)
        return addressRepository.findAll(specification, pageable).map { mapper.addressEntityToDTO(it) }.content
    }

    @LogInfo(logParameters = true)
    fun getById(id: Long) = addressRepository
        .findById(id)
        .getOrElse { throw ResourceNotFoundException("Address not found with id: $id") }
        .let { mapper.addressEntityToDTO(it) }

}