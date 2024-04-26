package com.br.qualrole.dto

data class AddressDTO(
    val id: Long? = null,
    val district: String,
    val streetName: String,
    val zipCode: String,
    val city: String,
    val state: String
)
