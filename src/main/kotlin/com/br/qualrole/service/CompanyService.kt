package com.br.qualrole.service

import com.br.qualrole.controller.company.request.CompanyRequest
import com.br.qualrole.domain.entity.CompanyEntity
import com.br.qualrole.dto.CompanyDTO

interface CompanyService {
    fun create(companyRequest: CompanyRequest): CompanyDTO

    fun getCompanyById(id: Long):  CompanyDTO

    fun save(companyEntity: CompanyEntity): CompanyEntity
}