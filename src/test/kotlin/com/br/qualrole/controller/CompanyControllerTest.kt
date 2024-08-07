package com.br.qualrole.controller

import com.br.qualrole.IntegrationTest
import com.br.qualrole.builder.AddressBuilder
import com.br.qualrole.builder.CompanyBuilder
import com.br.qualrole.dto.CompanyDTO
import com.br.qualrole.exception.ResourceAlreadyExistsException
import com.br.qualrole.exception.ResourceNotFoundException
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

private const val COMPANY_PATH = "/company"

class CompanyControllerTest : IntegrationTest() {

    @Test
    fun `must return company by id`() {
        val addressEntity = addressRepository.save(AddressBuilder.giveAddressEntity())
        val companyEntity = companyRepository.save(CompanyBuilder.giveCompanyEntity(address = addressEntity))

        val response = mockMvc.get("$COMPANY_PATH/${companyEntity.id}")
            .andExpect { status { isOk() } }
            .andReturn().response.contentAsString
            .let { objectMapper.readValue<CompanyDTO>(it) }

        assertThat(response).usingRecursiveComparison().isEqualTo(companyEntity)
    }

    @Test
    fun `should throw ResourceNotFoundException when company not found`() {
        val exception = mockMvc.get("$COMPANY_PATH/${System.nanoTime()}")
            .andExpect { status { isNotFound() } }
            .andReturn().resolvedException

        assertThat(exception).isExactlyInstanceOf(ResourceNotFoundException::class.java)
    }

    @Test
    fun `should throw ResourceAlreadyExistsException when there is already a company with the same document`() {
        val addressEntity = addressRepository.save(AddressBuilder.giveAddressEntity())
        val document = companyRepository.save(CompanyBuilder.giveCompanyEntity(address = addressEntity)).document

        val companyRequest = CompanyBuilder.giveCompanyRequest(document = document, addressId = addressEntity.id!!)

        val request = post("$COMPANY_PATH/create")
            .content(objectMapper.writeValueAsString(companyRequest))
            .contentType(MediaType.APPLICATION_JSON)

        val exception = mockMvc
            .perform(request)
            .andExpect { status().isUnprocessableEntity }
            .andReturn().resolvedException

        assertThat(exception).isExactlyInstanceOf(ResourceAlreadyExistsException::class.java)
    }

    @Test
    fun `must be created company`() {
        val addressId = addressRepository.save(AddressBuilder.giveAddressEntity()).id
        val companyRequest = CompanyBuilder.giveCompanyRequest(addressId = addressId!!)

        val request = post("$COMPANY_PATH/create")
            .content(objectMapper.writeValueAsString(companyRequest))
            .contentType(MediaType.APPLICATION_JSON)

        val response = mockMvc
            .perform(request)
            .andExpect { status().isCreated }
            .andReturn().response.getContentAsString(Charsets.UTF_8)
            .let { objectMapper.readValue<CompanyDTO>(it) }

        assertThat(response)
            .usingRecursiveComparison()
            .ignoringFields("id", "address", "logoImageUrl")
            .isEqualTo(companyRequest)

        assertThat(response.id).isNotNull()
        assertThat(response.address.id).isEqualTo(addressId)
        assertThat(response.logoImageUrl).isNotNull()
    }
}
