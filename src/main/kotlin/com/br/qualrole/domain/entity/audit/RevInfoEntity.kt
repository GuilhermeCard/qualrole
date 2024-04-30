package com.br.qualrole.domain.entity.audit

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.envers.RevisionEntity
import org.hibernate.envers.RevisionNumber
import org.hibernate.envers.RevisionTimestamp
import java.time.LocalDateTime


@Entity
@RevisionEntity
@Table(name = "revinfo")
class RevInfoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "rev")
    val revisionNumber: Long? = null,

    @RevisionTimestamp
    @Column(name = "revtstmp")
    val revisionTimestamp: LocalDateTime? = null,

    val username: String? = null
)