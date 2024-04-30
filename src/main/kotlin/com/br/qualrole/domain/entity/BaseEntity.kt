package com.br.qualrole.domain.entity

import jakarta.persistence.Column
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

open class BaseEntity (
    @CreationTimestamp
    @Column(name = "dat_creation")
    var datCreation: LocalDateTime? = null,

    @UpdateTimestamp
    @Column(name = "dat_update")
    var datUpdate: LocalDateTime? = null
)
