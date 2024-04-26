package com.br.qualrole.controller.address.request

data class AddressFilterRequest(
    val id: Long?,
    val district: String?,
    val streetName: String?,
    val zipCode: String?,
    val city: String?,
    val state: String?
)