package com.br.qualrole.builder

import br.com.caelum.stella.validation.CNPJValidator
import com.br.qualrole.controller.company.request.CompanyRequest
import com.br.qualrole.domain.entity.AddressEntity
import com.br.qualrole.domain.entity.CompanyEntity
import com.br.qualrole.domain.entity.SocialNetwork
import com.br.qualrole.enums.CategoryEnum
import com.br.qualrole.enums.SocialNetworkType
import java.net.URI
import java.time.LocalTime

object CompanyBuilder {
    fun giveCompanyEntity(
        document: String? = null,
        name: String? = null,
        address: AddressEntity,
        phone: String? = null,
        socialNetwork: List<SocialNetwork>? = null,
        addressNumber: String? = null
    ) = CompanyEntity(
        document = document ?: CNPJValidator().generateRandomValid(),
        name = name ?: "Maya e Gustavo Pizzaria Ltda",
        address = address,
        phone = phone ?: "37997385734",
        socialNetwork = socialNetwork ?: listOf(
            SocialNetwork(
                type = SocialNetworkType.INSTAGRAM,
                link = URI("https://instagram.com.br").toURL()
            )
        ),
        addressNumber = addressNumber ?: "1500",
        category = CategoryEnum.PUB,
        description = """
                        Lorem Ipsum is simply dummy text of the printing and typesetting industry. 
                        Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, 
                        when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                      """.trim(),
        companyImages = listOf(URI("https://instagram.com.br/companyimage").toURL()),
        startOpeningHour = LocalTime.of(8, 0),
        endOpeningHour = LocalTime.of(18, 0),
        operatingDays = listOf(1, 2, 3, 4, 5),
        logoImageUrl = URI("https://instagram.com.br/companyimage").toURL(),
    )

    fun giveCompanyRequest(document: String? = null, addressId: Long) = CompanyRequest(
        document = document ?: CNPJValidator().generateRandomValid(),
        name = "Osvaldo e Rafaela Eletrônica ME",
        addressId = addressId,
        phone = "31983696712",
        socialNetwork = emptyList(),
        addressNumber = "355",
        category = CategoryEnum.RESTAURANT,
        description = """
                        Lorem Ipsum is simply dummy text of the printing and typesetting industry. 
                        Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, 
                        when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                      """.trim(),
        companyImages = emptyList(),
        startOpeningHour = LocalTime.of(8, 0),
        endOpeningHour = LocalTime.of(22, 0),
        operatingDays = listOf(1, 2, 3, 4, 5)
    )

}
