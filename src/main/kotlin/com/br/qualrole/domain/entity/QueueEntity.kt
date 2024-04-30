package com.br.qualrole.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.envers.AuditTable
import org.hibernate.envers.Audited

@Entity
@Table(name = "queue")
@Audited
@AuditTable("queue_audit")
data class QueueEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "present_people")
    var presentPeople: Long,

    @Column(name = "max_capacity")
    var maxCapacity: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    var company: CompanyEntity

): BaseEntity()
