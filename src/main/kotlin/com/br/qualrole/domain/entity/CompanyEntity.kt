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
import jakarta.persistence.Table
import org.hibernate.envers.AuditTable
import org.hibernate.envers.Audited
import java.net.URL

@Entity
@Table(name = "company")
@Audited
@AuditTable("company_audit")
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
    var addressComplement: String? = null

): BaseEntity()

data class SocialNetwork(
    val type: SocialNetworkType,
    val link: URL
)