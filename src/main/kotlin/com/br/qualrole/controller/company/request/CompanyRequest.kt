package com.br.qualrole.controller.company.request

import com.br.qualrole.domain.entity.SocialNetwork
import com.br.qualrole.enums.CategoryEnum
import java.net.URL
import java.time.LocalTime

data class CompanyRequest(
    var document: String,
    val name: String,
    val addressId: Long,
    val phone: String,
    val socialNetwork: List<SocialNetwork>,
    val addressNumber: String,
    val addressComplement: String? = null,
    val category: CategoryEnum,
    val description: String? = null,
    var logoImageUrl: URL? = null,
    val companyImages: List<URL>,
    var startOpeningHour: LocalTime,
    var endOpeningHour: LocalTime,
    var operatingDays: List<Int>,
    var isOpen: Boolean = false
)