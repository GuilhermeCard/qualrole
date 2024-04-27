package com.br.qualrole.domain.repository

import com.br.qualrole.domain.entity.AddressEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : JpaSpecificationExecutor<AddressEntity>, JpaRepository<AddressEntity, Long> {

}