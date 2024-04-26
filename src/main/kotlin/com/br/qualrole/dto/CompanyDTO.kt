package com.br.qualrole.dto

import com.br.qualrole.domain.entity.SocialNetwork

data class CompanyDTO(
    val id: Long? = null,
    var document: String,
    val name: String,
    val address: AddressDTO,
    val phone: String,
    val socialNetwork: List<SocialNetwork?>,
    val addressNumber: String,
    val addressComplement: String? = null
)
