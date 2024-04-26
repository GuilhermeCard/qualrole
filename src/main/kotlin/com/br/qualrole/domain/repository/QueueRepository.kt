package com.br.qualrole.domain.repository

import com.br.qualrole.domain.entity.QueueEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QueueRepository : JpaRepository<QueueEntity, Long> {

    fun findByCompanyId(companyId: Long): QueueEntity?
}