package com.br.qualrole

import com.br.qualrole.domain.repository.AddressRepository
import com.br.qualrole.domain.repository.CompanyRepository
import com.br.qualrole.domain.repository.QueueRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.junit.jupiter.Testcontainers

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@AutoConfigureMockMvc
class IntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var addressRepository: AddressRepository

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Autowired
    lateinit var queueRepository: QueueRepository

    @AfterEach
    fun cleanUpAfter() {
        queueRepository.deleteAll()
        companyRepository.deleteAll()
        addressRepository.deleteAll()
    }

}
