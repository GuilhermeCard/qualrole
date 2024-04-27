package com.br.qualrole.domain.repository

import com.br.qualrole.domain.entity.CompanyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository : JpaRepository<CompanyEntity, Long> {

    fun findFirstByDocument(document: String): CompanyEntity?

}