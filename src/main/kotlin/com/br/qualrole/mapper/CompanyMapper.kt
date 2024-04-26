package com.br.qualrole.mapper

import com.br.qualrole.domain.entity.CompanyEntity
import com.br.qualrole.dto.CompanyDTO
import org.mapstruct.Mapper

@Mapper
interface CompanyMapper {

    fun companyEntityToDTO(companyEntity: CompanyEntity): CompanyDTO

    fun companyDTOToEntity(companyDTO: CompanyDTO): CompanyEntity
}