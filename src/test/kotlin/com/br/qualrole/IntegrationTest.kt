package com.br.qualrole

import com.br.qualrole.domain.repository.AddressRepository
import com.br.qualrole.domain.repository.CompanyRepository
import com.br.qualrole.domain.repository.QueueRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.junit.jupiter.Testcontainers

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

}
