package com.br.qualrole.domain.entity.audit

import com.br.qualrole.listener.CustomRevisionListener
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
@RevisionEntity(CustomRevisionListener::class)
@Table(name = "revinfo")
class RevisionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "rev")
    val revisionNumber: Long? = null,

    @RevisionTimestamp
    @Column(name = "revtstmp")
    val datRevision: LocalDateTime? = null,

    @Column(name = "username")
    var username: String? = null
)