package com.br.qualrole.dto

import com.br.qualrole.domain.entity.SocialNetwork
import com.br.qualrole.enums.CategoryEnum
import java.net.URL
import java.time.LocalTime

data class CompanyDTO(
    val id: Long? = null,
    val document: String,
    val name: String,
    val address: AddressDTO,
    val phone: String,
    val socialNetwork: List<SocialNetwork>,
    val addressNumber: String,
    val addressComplement: String?,
    val category: CategoryEnum,
    val description: String? = null,
    val logoImageUrl: URL,
    val companyImages: List<URL>,
    val startOpeningHour: LocalTime,
    val endOpeningHour: LocalTime,
    val operatingDays: List<Int>,
    val isOpen: Boolean
)
