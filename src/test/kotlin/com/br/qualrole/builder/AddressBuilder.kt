package com.br.qualrole.builder

import com.br.qualrole.domain.entity.AddressEntity

object AddressBuilder {
    fun giveAddressEntity(
        district: String? = null,
        streetName: String? = null,
        zipCode: String? = null,
        city: String? = null,
        state: String? = null
    ) = AddressEntity(
        district = district ?: "Jardim Patricia",
        streetName = streetName ?: "Av Jose Fonseca e Silva",
        zipCode = zipCode ?: "38400500",
        city = city ?: "Ubercity",
        state = state ?: "MG"
    )

}
