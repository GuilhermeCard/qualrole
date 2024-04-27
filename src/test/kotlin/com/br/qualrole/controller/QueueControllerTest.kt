package com.br.qualrole.controller

import com.br.qualrole.IntegrationTest
import com.br.qualrole.builder.AddressBuilder
import com.br.qualrole.builder.CompanyBuilder
import com.br.qualrole.builder.QueueBuilder
import com.br.qualrole.dto.QueueDTO
import com.br.qualrole.exception.ResourceAlreadyExistsException
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

private const val QUEUE_PATH = "/queue"

class QueueControllerTest : IntegrationTest() {

    @Test
    fun `should create a queue successfully`() {
        val addressEntity = addressRepository.save(AddressBuilder.giveAddressEntity())
        val companyEntity = companyRepository.save(CompanyBuilder.giveCompanyEntity(address = addressEntity))

        val queueRequest = QueueBuilder.giveQueueRequest(companyId = companyEntity.id!!)

        val request = post("$QUEUE_PATH/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(queueRequest))

        val response = mockMvc.perform(request)
            .andExpect(status().isOk)
            .andReturn().response.contentAsString
            .let { objectMapper.readValue<QueueDTO>(it) }

        assertThat(response.id).isNotNull()
        assertThat(response.maxCapacity).isEqualTo(queueRequest.maxCapacity)
        assertThat(response.presentPeople).isEqualTo(queueRequest.presentPeople)
        assertThat(response.company!!.document).isEqualTo(companyEntity.document)
    }

    @Test
    fun `should launch ResourceAlreadyExistsException when there is already a queue registered with this id`() {
        val addressEntity = addressRepository.save(AddressBuilder.giveAddressEntity())
        val companyEntity = companyRepository.save(CompanyBuilder.giveCompanyEntity(address = addressEntity))
        val queueEntity = queueRepository.save(QueueBuilder.giveQueueEntity(companyEntity))
        val queueRequest = QueueBuilder.giveQueueRequest(id = queueEntity.id, companyId = companyEntity.id!!)

        val request = post("$QUEUE_PATH/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(queueRequest))

        val exception = mockMvc.perform(request)
            .andExpect(status().isUnprocessableEntity)
            .andReturn().resolvedException

        assertThat(exception).isExactlyInstanceOf(ResourceAlreadyExistsException::class.java)
        assertThat(exception).hasMessage("Queue already exists with id: ${queueRequest.id}")
    }

    @Test
    fun `should launch ResourceAlreadyExistsException when there is already a queue registered with companyId`() {
        val addressEntity = addressRepository.save(AddressBuilder.giveAddressEntity())
        val companyEntity = companyRepository.save(CompanyBuilder.giveCompanyEntity(address = addressEntity))
        queueRepository.save(QueueBuilder.giveQueueEntity(companyEntity))
        val queueRequest = QueueBuilder.giveQueueRequest(companyId = companyEntity.id!!)

        val request = post("$QUEUE_PATH/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(queueRequest))

        val exception = mockMvc.perform(request)
            .andExpect(status().isUnprocessableEntity)
            .andReturn().resolvedException

        assertThat(exception).isExactlyInstanceOf(ResourceAlreadyExistsException::class.java)
        assertThat(exception).hasMessage("Queue already exists with companyId: ${queueRequest.companyId}")
    }

    @Test
    fun `must return all registered queues`() {
        val addressEntity = addressRepository.save(AddressBuilder.giveAddressEntity())

        repeat(3) {
            val companyEntity = companyRepository.save(CompanyBuilder.giveCompanyEntity(address = addressEntity))
            queueRepository.save(QueueBuilder.giveQueueEntity(companyEntity))
        }

        val response = mockMvc.get(QUEUE_PATH)
            .andExpect { status { isOk() } }
            .andReturn().response.contentAsString
            .let { objectMapper.readValue<List<QueueDTO>>(it) }

        assertThat(response).isNotEmpty
        assertThat(response).size().isEqualTo(3)
    }
}