package com.br.qualrole.service.impl

import com.br.qualrole.annotation.LogInfo
import com.br.qualrole.controller.address.request.AddressFilterRequest
import com.br.qualrole.domain.repository.AddressRepository
import com.br.qualrole.dto.AddressDTO
import com.br.qualrole.exception.ResourceNotFoundException
import com.br.qualrole.mapper.toAddressDTO
import com.br.qualrole.mapper.toAddressEntity
import com.br.qualrole.service.AddressService
import com.br.qualrole.service.specification.AddressSpecification
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class AddressServiceImpl(
    val addressRepository: AddressRepository
): AddressService {

    @LogInfo(logParameters = true)
    override fun create(body: AddressDTO) = addressRepository.save(body.toAddressEntity()).toAddressDTO()

    @LogInfo(logParameters = true)
    override fun getByFilterSpecification(
        filter: AddressFilterRequest,
        pageable: Pageable
    ): List<AddressDTO> {
        val specification = AddressSpecification.searchWithSpecification(filter)
        return addressRepository.findAll(specification, pageable).map { it.toAddressDTO() }.content
    }

    @LogInfo(logParameters = true)
    override fun getById(id: Long) = addressRepository
    .findById(id)
    .getOrElse { throw ResourceNotFoundException("Address not found with id: $id") }
    .toAddressDTO()

}