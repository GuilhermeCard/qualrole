package com.br.qualrole.service.address

import com.br.qualrole.controller.address.request.AddressFilterRequest
import com.br.qualrole.domain.repository.AddressRepository
import com.br.qualrole.dto.AddressDTO
import com.br.qualrole.exception.ResourceNotFoundException
import com.br.qualrole.mapper.AddressMapper
import com.br.qualrole.service.address.specification.AddressSpecification
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class AddressService(
    val addressSpecification: AddressSpecification,
    val mapper: AddressMapper,
    val addressRepository: AddressRepository
) {

    fun create(body: AddressDTO) = mapper.addressDTOtoEntity(body)
        .let { addressRepository.save(it) }
        .let { mapper.addressEntityToDTO(it) }

    fun getByFilterSpecification(filter: AddressFilterRequest): List<AddressDTO> {
        val specification = addressSpecification.searchWithSpecification(filter)
        return addressRepository.findAll(specification).map { mapper.addressEntityToDTO(it) }
    }

    fun getById(id: Long) = addressRepository
        .findById(id)
        .getOrElse { throw ResourceNotFoundException("Address not found with id: $id") }
        .let { mapper.addressEntityToDTO(it) }

}