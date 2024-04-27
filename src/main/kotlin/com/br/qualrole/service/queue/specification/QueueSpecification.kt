package com.br.qualrole.service.queue.specification

import com.br.qualrole.controller.queue.request.QueueFilterRequest
import com.br.qualrole.domain.entity.QueueEntity
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

object QueueSpecification {
    fun searchWithSpecification(specification: QueueFilterRequest): Specification<QueueEntity> {
        return Specification { root: Root<QueueEntity>, _: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            specification.companyName
                ?.let {
                    predicates.add(
                        criteriaBuilder.like(
                            criteriaBuilder.lower(root.get<String?>("company").get("name")),
                            "%${it.lowercase()}%"
                        )
                    )
                }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}