package com.br.qualrole.service.company

import com.br.qualrole.domain.repository.CompanyRepository
import com.br.qualrole.dto.CompanyDTO
import com.br.qualrole.exception.ResourceAlreadyExists
import com.br.qualrole.exception.ResourceNotFoundException
import com.br.qualrole.mapper.CompanyMapper
import com.br.qualrole.utils.formatToCPForCNPJ
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CompanyService(
    val mapper: CompanyMapper,
    val repository: CompanyRepository
) {

    fun create(companyDTO: CompanyDTO) = companyDTO
        .apply { this.document = this.document.formatToCPForCNPJ() }
        .let { repository.findFirstByDocument(companyDTO.document) }
        ?.let { throw ResourceAlreadyExists("Company already exists with document: ${it.document}") }
        ?: repository.save(mapper.companyDTOToEntity(companyDTO))
            .let { mapper.companyEntityToDTO(it) }

    fun getCompanyById(id: Long) =
        repository.findById(id).getOrNull()?.let { mapper.companyEntityToDTO(it) }
            ?: throw ResourceNotFoundException("Company not found with id: $id")
}