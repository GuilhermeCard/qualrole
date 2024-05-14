package com.br.qualrole.mapper

import com.br.qualrole.controller.company.request.CompanyRequest
import com.br.qualrole.domain.entity.CompanyEntity
import com.br.qualrole.dto.AddressDTO
import com.br.qualrole.dto.CompanyDTO

fun CompanyRequest.toCompanyDTO(addressDTO: AddressDTO) = CompanyDTO(
    document = this.document,
    name = this.name,
    address = addressDTO,
    phone = this.phone,
    socialNetwork = this.socialNetwork,
    addressNumber = this.addressNumber,
    addressComplement = this.addressComplement,
    category = this.category,
    description = this.description?.trim(),
    logoImageUrl = this.logoImageUrl
        ?: let { throw IllegalArgumentException("${this::logoImageUrl.name} cannot be null") },
    companyImages = this.companyImages,
    startOpeningHour = this.startOpeningHour,
    endOpeningHour = this.endOpeningHour,
    operatingDays = this.operatingDays,
    isOpen = this.isOpen
)

fun CompanyEntity.toCompanyDTO() = CompanyDTO(
    id = this.id,
    document = this.document,
    name = this.name,
    address = this.address.toAddressDTO(),
    phone = this.phone,
    socialNetwork = this.socialNetwork,
    addressNumber = this.addressNumber,
    addressComplement = this.addressComplement,
    category = this.category,
    description = this.description?.trim(),
    logoImageUrl = this.logoImageUrl,
    companyImages = this.companyImages,
    startOpeningHour = this.startOpeningHour,
    endOpeningHour = this.endOpeningHour,
    operatingDays = this.operatingDays,
    isOpen = this.isOpen
)

fun CompanyDTO.toCompanyEntity() = CompanyEntity(
    id = this.id,
    document = this.document,
    name = this.name,
    address = this.address.toAddressEntity(),
    phone = this.phone,
    socialNetwork = this.socialNetwork,
    addressNumber = this.addressNumber,
    addressComplement = null,
    category = this.category,
    description = this.description?.trim(),
    logoImageUrl = this.logoImageUrl,
    companyImages = this.companyImages,
    startOpeningHour = this.startOpeningHour,
    endOpeningHour = this.endOpeningHour,
    operatingDays = this.operatingDays,
    isOpen = this.isOpen
)
