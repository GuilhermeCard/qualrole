package com.br.qualrole.controller

import com.br.qualrole.IntegrationTest
import com.br.qualrole.builder.AddressBuilder
import com.br.qualrole.builder.CompanyBuilder
import com.br.qualrole.builder.QueueBuilder
import com.br.qualrole.controller.queue.request.QueueFilterRequest
import com.br.qualrole.controller.queue.request.UpdateSeatsRequest
import com.br.qualrole.dto.QueueDTO
import com.br.qualrole.exception.ResourceAlreadyExistsException
import com.br.qualrole.exception.ResourceNotFoundException
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
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
    fun `ResourceNotFoundException should be thrown when the queue does not exist`() {
        val queueId = System.nanoTime()
        val updateSeatsRequest = UpdateSeatsRequest(presentPeople = 10)

        val request = put("$QUEUE_PATH/{queueId}", queueId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateSeatsRequest))

        val exception = mockMvc.perform(request)
            .andExpect(status().isNotFound)
            .andReturn().resolvedException

        assertThat(exception).isExactlyInstanceOf(ResourceNotFoundException::class.java)
        assertThat(exception).hasMessage("Queue not found with id: $queueId")
    }

    @Test
    fun `must return all registered queues`() {
        val addressEntity = addressRepository.save(AddressBuilder.giveAddressEntity())

        repeat(3) {
            val companyEntity = companyRepository.save(CompanyBuilder.giveCompanyEntity(address = addressEntity))
            queueRepository.save(QueueBuilder.giveQueueEntity(companyEntity))
        }

        val queueList = queueRepository.findAll().sortedBy { it.id }

        val response = mockMvc.get(QUEUE_PATH)
            .andExpect { status { isOk() } }
            .andReturn().response.getContentAsString(Charsets.UTF_8)
            .let { objectMapper.readValue<List<QueueDTO>>(it) }
            .sortedBy { it.id }

        assertThat(response.size).isEqualTo(queueList.size)
        response.forEachIndexed { i, queueDTO ->
            assertThat(queueDTO.id).isEqualTo(queueList[i].id)
            assertThat(queueDTO.presentPeople).isEqualTo(queueList[i].presentPeople)
            assertThat(queueDTO.maxCapacity).isEqualTo(queueList[i].maxCapacity)
            assertThat(queueDTO.company?.id).isEqualTo(queueList[i].company.id)
            assertThat(queueDTO.company?.name).isEqualTo(queueList[i].company.name)
            assertThat(queueDTO.company?.document).isEqualTo(queueList[i].company.document)
        }
    }

    @Test
    fun `should update the queue properties successfully`() {
        val addressEntity = addressRepository.save(AddressBuilder.giveAddressEntity())
        val companyEntity = companyRepository.save(CompanyBuilder.giveCompanyEntity(address = addressEntity))

        val queueEntity = queueRepository.save(QueueBuilder.giveQueueEntity(companyEntity))
        val updateSeatsRequest = UpdateSeatsRequest(presentPeople = 20)

        val request = put("$QUEUE_PATH/{queueId}", queueEntity.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateSeatsRequest))

        val response = mockMvc.perform(request)
            .andExpect(status().isOk)
            .andReturn().response.contentAsString
            .let { objectMapper.readValue<QueueDTO>(it) }

        assertThat(response.presentPeople).isNotEqualTo(queueEntity.presentPeople)
        assertThat(response.presentPeople).isEqualTo(updateSeatsRequest.presentPeople)
        assertThat(response.maxCapacity).isEqualTo(queueEntity.maxCapacity)
    }

    @Test
    fun `must return queue filtered by parameters`() {
        val addressEntity = addressRepository.save(AddressBuilder.giveAddressEntity())
        repeat(3) { companyRepository.save(CompanyBuilder.giveCompanyEntity(address = addressEntity)) }
        val companyEntity = companyRepository.save(
            CompanyBuilder.giveCompanyEntity(
                name = "Sankhya Gestão de Negocios LTDA",
                address = addressEntity
            )
        )
        queueRepository.save(QueueBuilder.giveQueueEntity(companyEntity))

        val filter = QueueFilterRequest(companyName = "SANKHYA")

        val response = mockMvc
            .perform(get(QUEUE_PATH).queryParam(filter::companyName.name, filter.companyName))
            .andExpect(status().isOk)
            .andReturn().response.getContentAsString(Charsets.UTF_8)
            .let { objectMapper.readValue<List<QueueDTO>>(it) }

        assertThat(response).size().isOne
        assertThat(response[0].company!!.id).isEqualTo(companyEntity.id)
        assertThat(response[0].company!!.name).isEqualTo(companyEntity.name)
        assertThat(response[0].company!!.document).isEqualTo(companyEntity.document)
    }
}