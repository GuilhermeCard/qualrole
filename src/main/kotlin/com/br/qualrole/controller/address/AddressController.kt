package com.br.qualrole.controller.address

import com.br.qualrole.annotation.LogInfo
import com.br.qualrole.controller.address.request.AddressFilterRequest
import com.br.qualrole.dto.AddressDTO
import com.br.qualrole.service.address.AddressService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/address")
class AddressController(val addressService: AddressService) {

    @LogInfo(logParameters = true)
    @PostMapping("/create")
    fun create(@RequestBody body: AddressDTO): ResponseEntity<AddressDTO> {
        val response = addressService.create(body)
        return ResponseEntity.created(URI.create("/address?id=${response.id}")).body(response)
    }

    @LogInfo(logParameters = true)
    @GetMapping
    fun getByFilter(filter: AddressFilterRequest, pageable: Pageable) = addressService.getByFilterSpecification(filter, pageable)

}