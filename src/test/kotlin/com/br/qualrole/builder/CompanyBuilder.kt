package com.br.qualrole.builder

import br.com.caelum.stella.validation.CNPJValidator
import com.br.qualrole.controller.company.request.CompanyRequest
import com.br.qualrole.domain.entity.AddressEntity
import com.br.qualrole.domain.entity.CompanyEntity
import com.br.qualrole.domain.entity.SocialNetwork
import com.br.qualrole.enums.SocialNetworkType
import java.net.URI

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
        addressNumber = addressNumber ?: "1500"
    )

    fun giveCompanyRequest(document: String? = null, addressId: Long) = CompanyRequest(
        document = document ?: CNPJValidator().generateRandomValid(),
        name = "Osvaldo e Rafaela Eletrônica ME",
        addressId = addressId,
        phone = "31983696712",
        socialNetwork = emptyList(),
        addressNumber = "355"
    )

}
