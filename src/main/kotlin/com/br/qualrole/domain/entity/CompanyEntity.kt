package com.br.qualrole.domain.entity

import com.br.qualrole.converter.IntListConverter
import com.br.qualrole.converter.SocialNetworkListConverter
import com.br.qualrole.converter.StringListConverter
import com.br.qualrole.enums.CategoryEnum
import com.br.qualrole.enums.SocialNetworkType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.envers.AuditTable
import org.hibernate.envers.Audited
import java.net.URL
import java.time.LocalDateTime
import java.time.LocalTime

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

    @Column(name = "social_network", columnDefinition = "TEXT")
    @Convert(converter = SocialNetworkListConverter::class)
    var socialNetwork: List<SocialNetwork>,

    @Column(name = "address_number")
    var addressNumber: String,

    @Column(name = "address_complement")
    var addressComplement: String? = null,

    @Enumerated(EnumType.STRING)
    @Column
    var category: CategoryEnum,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "logo_image_url", columnDefinition = "TEXT")
    var logoImageUrl: URL,

    @Column(name = "company_images", columnDefinition = "TEXT")
    @Convert(converter = StringListConverter::class)
    var companyImages: List<URL>,

    @Column(name = "start_opening_hour")
    var startOpeningHour: LocalTime,

    @Column(name = "end_opening_hour")
    var endOpeningHour: LocalTime,

    @Column(name = "operating_days")
    @Convert(converter = IntListConverter::class)
    var operatingDays: List<Int>,

    @Column(name = "is_open")
    var isOpen: Boolean = false,

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