package com.br.qualrole.controller.company

import com.br.qualrole.dto.CompanyDTO
import com.br.qualrole.service.company.CompanyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/company")
class CompanyController(val companyService: CompanyService) {

    @PostMapping("/create")
    fun create(@RequestBody body: CompanyDTO) = companyService.create(body)

    @GetMapping("/{id}")
    fun retrieveCompany(@PathVariable id: Long) = companyService.getCompanyById(id)
}