package com.br.qualrole.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity(name = "queue")
data class QueueEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long,

    @Column(name = "present_people")
    var presentPeople: Long,

    @Column(name = "max_capacity")
    var maxCapacity: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    var company: CompanyEntity,

    @CreationTimestamp
    @Column(name = "dat_creation")
    var datCreation: LocalDateTime? = null,

    @UpdateTimestamp
    @Column(name = "dat_update")
    var datUpdate: LocalDateTime? = null
)
