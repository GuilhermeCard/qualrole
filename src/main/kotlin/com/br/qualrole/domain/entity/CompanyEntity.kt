package com.br.qualrole.domain.entity

import com.br.qualrole.converter.StringListConverter
import com.br.qualrole.enums.SocialNetworkType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.net.URL
import java.time.LocalDateTime

@Entity(name = "company")
data class CompanyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long? = null,

    @Column
    var document: String,

    @Column
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    var address: AddressEntity,

    @Column
    var phone: String,

    @Column(name = "social_network")
    @Convert(converter = StringListConverter::class)
    var socialNetwork: List<SocialNetwork?>,

    @Column(name = "address_number")
    var addressNumber: String,

    @Column(name = "address_complement")
    var addressComplement: String? = null,

    @CreationTimestamp
    @Column(name = "dat_creation")
    var datCreation: LocalDateTime? = null,

    @UpdateTimestamp
    @Column(name = "dat_update")
    var datUpdate: LocalDateTime? = null
)

data class SocialNetwork(
    val type: SocialNetworkType,
    val link: URL
)