package com.br.qualrole.service

import com.br.qualrole.controller.address.request.AddressFilterRequest
import com.br.qualrole.dto.AddressDTO
import org.springframework.data.domain.Pageable

interface AddressService {
    fun create(body: AddressDTO): AddressDTO

    fun getByFilterSpecification(
        filter: AddressFilterRequest,
        pageable: Pageable
    ): List<AddressDTO>

    fun getById(id: Long): AddressDTO
}