package com.br.qualrole.service.address.specification

import com.br.qualrole.controller.address.request.AddressFilterRequest
import com.br.qualrole.domain.entity.AddressEntity
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class AddressSpecification {

    fun searchWithSpecification(specification: AddressFilterRequest): Specification<AddressEntity> {
        return Specification { root: Root<AddressEntity>, _: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            specification.id?.let {
                predicates.add(criteriaBuilder.equal(root.get<Long>("id"), it))
            }

            specification.district?.let {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("district")), it.lowercase()))
            }

            specification.streetName?.let {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("streetName")), "%${it.lowercase()}%"))
            }

            specification.zipCode?.let {
                predicates.add(criteriaBuilder.equal(root.get<String>("zipCode"), it))
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}