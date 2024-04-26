package com.br.qualrole.mapper

import com.br.qualrole.domain.entity.AddressEntity
import com.br.qualrole.dto.AddressDTO
import org.mapstruct.Mapper

@Mapper
interface AddressMapper {

    fun addressEntityToDTO(addressEntity: AddressEntity): AddressDTO

    fun addressDTOtoEntity(addressDTO: AddressDTO): AddressEntity
}