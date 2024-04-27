package com.br.qualrole.service.company

import com.br.qualrole.controller.company.request.CompanyRequest
import com.br.qualrole.domain.repository.CompanyRepository
import com.br.qualrole.exception.ResourceAlreadyExistsException
import com.br.qualrole.exception.ResourceNotFoundException
import com.br.qualrole.mapper.CompanyMapper
import com.br.qualrole.service.address.AddressService
import com.br.qualrole.utils.formatToCPForCNPJ
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CompanyService(
    val mapper: CompanyMapper,
    val addressService: AddressService,
    val repository: CompanyRepository
) {

    fun create(companyRequest: CompanyRequest) = companyRequest
        .apply { this.document = this.document.formatToCPForCNPJ() }
        .let { repository.findFirstByDocument(companyRequest.document) }
        ?.let { throw ResourceAlreadyExistsException("Company already exists with document: ${it.document}") }
        ?: addressService.getById(companyRequest.addressId)
            .let { mapper.companyRequestToDTO(companyRequest).apply { this.address = it } }
            .let { repository.save(mapper.companyDTOToEntity(it)) }
            .let { mapper.companyEntityToDTO(it) }

    fun getCompanyById(id: Long) =
        repository.findById(id).getOrNull()?.let { mapper.companyEntityToDTO(it) }
            ?: throw ResourceNotFoundException("Company not found with id: $id")
}