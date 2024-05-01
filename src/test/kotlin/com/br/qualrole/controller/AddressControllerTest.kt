package com.br.qualrole.controller

import com.br.qualrole.IntegrationTest
import com.br.qualrole.builder.AddressBuilder
import com.br.qualrole.controller.address.request.AddressFilterRequest
import com.br.qualrole.dto.AddressDTO
import com.br.qualrole.mapper.AddressMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

private const val ADDRESS_PATH = "/address"

class AddressControllerTest : IntegrationTest() {

    @Autowired
    lateinit var mapper: AddressMapper

    @Test
    fun `must return all addresses`() {
        val addressEntity = AddressBuilder.giveAddressEntity()
        addressRepository.save(addressEntity)

        val addressList = addressRepository.findAll().sortedBy { it.id }

        val response = mockMvc.get(ADDRESS_PATH)
            .andExpect { status { isOk() } }
            .andReturn().response.contentAsString
            .let { objectMapper.readValue<List<AddressDTO>>(it) }
            .sortedBy { it.id }

        assertThat(response.size).isEqualTo(addressList.size)
        response.forEachIndexed { i, addressDTO ->
            assertThat(addressDTO.streetName).isEqualTo(addressList[i].streetName)
            assertThat(addressDTO.state).isEqualTo(addressList[i].state)
            assertThat(addressDTO.city).isEqualTo(addressList[i].city)
            assertThat(addressDTO.district).isEqualTo(addressList[i].district)
        }

    }

    @Test
    fun `must return address filtered by parameters`() {
        repeat(3) { addressRepository.save(AddressBuilder.giveAddressEntity()) }
        val addressEntity = addressRepository.save(AddressBuilder.giveAddressEntity(district = "Pacaembu"))
        addressRepository.save(addressEntity.copy(id = null))

        val filter = AddressFilterRequest(
            id = addressEntity.id,
            district = addressEntity.district,
            streetName = addressEntity.streetName,
            zipCode = addressEntity.zipCode,
            city = addressEntity.city,
            state = addressEntity.state
        )

        val response = mockMvc.perform(
            get(ADDRESS_PATH)
                .queryParam(filter::id.name, filter.id?.toString())
                .queryParam(filter::district.name, filter.district)
                .queryParam(filter::streetName.name, filter.streetName)
                .queryParam(filter::zipCode.name, filter.zipCode)
                .queryParam(filter::city.name, filter.city)
                .queryParam(filter::state.name, filter.state)
        ).andExpect { status().isOk }
            .andReturn().response.contentAsString
            .let { objectMapper.readValue<List<AddressDTO>>(it) }

        assertThat(response).size().isOne
        assertThat(response[0].streetName).isEqualTo(addressEntity.streetName)
        assertThat(response[0].state).isEqualTo(addressEntity.state)
        assertThat(response[0].city).isEqualTo(addressEntity.city)
        assertThat(response[0].district).isEqualTo(addressEntity.district)
    }

    @Test
    fun `must return address empty list`() {
        val response = mockMvc.perform(get(ADDRESS_PATH).queryParam("id", System.nanoTime().toString()))
            .andExpect { status().isOk }
            .andReturn().response.contentAsString
            .let { objectMapper.readValue<List<AddressDTO>>(it) }

        assertThat(response).isEmpty()
    }

    @Test
    fun `must be created address`() {
        val addressDTO = AddressBuilder.giveAddressEntity().let { mapper.addressEntityToDTO(it) }

        val request = post("$ADDRESS_PATH/create", addressDTO)
            .content(objectMapper.writeValueAsString(addressDTO))
            .contentType(MediaType.APPLICATION_JSON)

        val result = mockMvc.perform(request).andReturn().response
        val response = objectMapper.readValue<AddressDTO>(result.contentAsString)

        assertThat(result.status).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.id).isNotNull()
        assertThat(addressDTO.district).isEqualTo(addressDTO.district)
        assertThat(addressDTO.streetName).isEqualTo(addressDTO.streetName)
        assertThat(addressDTO.city).isEqualTo(addressDTO.city)
        assertThat(addressDTO.zipCode).isEqualTo(addressDTO.zipCode)
        assertThat(addressDTO.state).isEqualTo(addressDTO.state)
    }
}
