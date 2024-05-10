package com.br.qualrole.mapper

import com.br.qualrole.domain.entity.AddressEntity
import com.br.qualrole.dto.AddressDTO

fun AddressEntity.toAddressDTO() = AddressDTO(
    id = this.id,
    district = this.district,
    streetName = this.streetName,
    zipCode = this.zipCode,
    city = this.city,
    state = this.state
)

fun AddressDTO.toAddressEntity() = AddressEntity(
    id = this.id,
    district = this.district,
    streetName = this.streetName,
    zipCode = this.zipCode,
    city = this.city,
    state = this.state
)
