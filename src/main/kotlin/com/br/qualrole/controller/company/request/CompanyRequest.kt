package com.br.qualrole.controller.company.request

import com.br.qualrole.domain.entity.SocialNetwork

data class CompanyRequest(
    val id: Long? = null,
    var document: String,
    val name: String,
    val addressId: Long,
    val phone: String,
    val socialNetwork: List<SocialNetwork?>,
    val addressNumber: String,
    val addressComplement: String? = null
)