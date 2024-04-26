package com.br.qualrole.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity(name = "address")
data class AddressEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "district")
    var district: String,

    @Column(name = "street_name")
    var streetName: String,

    @Column(name = "zip_code")
    var zipCode: String,

    @Column(name = "city")
    var city: String,

    @Column(name = "state")
    var state: String,

    @CreationTimestamp
    @Column(name = "dat_creation")
    var datCreation: LocalDateTime? = null,

    @UpdateTimestamp
    @Column(name = "dat_update")
    var datUpdate: LocalDateTime? = null
)
