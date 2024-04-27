package com.br.qualrole.controller.address.request

data class AddressFilterRequest(
    val id: Long? = null,
    val district: String? = null,
    val streetName: String? = null,
    val zipCode: String? = null,
    val city: String? = null,
    val state: String? = null
)