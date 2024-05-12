package com.br.qualrole.service.company

import br.com.caelum.stella.format.CNPJFormatter
import br.com.caelum.stella.validation.CNPJValidator
import com.br.qualrole.annotation.LogInfo
import com.br.qualrole.controller.company.request.CompanyRequest
import com.br.qualrole.domain.entity.CompanyEntity
import com.br.qualrole.domain.repository.CompanyRepository
import com.br.qualrole.dto.CompanyDTO
import com.br.qualrole.exception.ResourceAlreadyExistsException
import com.br.qualrole.exception.ResourceNotFoundException
import com.br.qualrole.mapper.toCompanyDTO
import com.br.qualrole.mapper.toCompanyEntity
import com.br.qualrole.service.address.AddressService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URI
import kotlin.jvm.optionals.getOrNull

@Service
class CompanyService(
    val addressService: AddressService,
    val repository: CompanyRepository,
    @Value("\${qual-role.default.company_logo_url}")
    val defaultLogoUrl: String
) {

    @LogInfo(logParameters = true)
    fun create(companyRequest: CompanyRequest): CompanyDTO {
        val document = unFormatAndValidateDocument(companyRequest.document)

        repository.findFirstByDocument(document)?.let {
            throw ResourceAlreadyExistsException("Company already exists with document: ${it.document}")
        }

        val address = addressService.getById(companyRequest.addressId)

        val companyDTO = companyRequest
            .apply { this.logoImageUrl = this.logoImageUrl ?: URI(defaultLogoUrl).toURL() }
            .toCompanyDTO(address)

        return repository.save(companyDTO.toCompanyEntity()).toCompanyDTO()

    }

    @LogInfo(logParameters = true)
    fun getCompanyById(id: Long) =
        repository.findById(id).getOrNull()?.toCompanyDTO()
            ?: throw ResourceNotFoundException("Company not found with id: $id")

    fun save(companyEntity: CompanyEntity) = repository.save(companyEntity)

    private fun unFormatAndValidateDocument(document: String): String {
        val result = CNPJFormatter().unformat(document)
        CNPJValidator().assertValid(result)
        return result
    }
}