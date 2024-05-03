package com.br.qualrole.controller.company

import com.br.qualrole.annotation.LogInfo
import com.br.qualrole.controller.company.request.CompanyRequest
import com.br.qualrole.dto.CompanyDTO
import com.br.qualrole.service.company.CompanyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/company")
class CompanyController(val companyService: CompanyService) {

    @LogInfo(logParameters = true)
    @PostMapping("/create")
    fun create(@RequestBody body: CompanyRequest): ResponseEntity<CompanyDTO> {
        val response = companyService.create(body)
        return ResponseEntity.created(URI.create("/company/${response.id}")).body(response)
    }

    @LogInfo(logParameters = true)
    @GetMapping("/{id}")
    fun retrieveCompany(@PathVariable id: Long) = companyService.getCompanyById(id)
}