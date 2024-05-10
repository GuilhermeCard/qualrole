package com.br.qualrole.converter

import com.br.qualrole.domain.entity.SocialNetwork
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class SocialNetworkListConverter(private val mapper: ObjectMapper) : AttributeConverter<List<SocialNetwork>, String> {

    override fun convertToDatabaseColumn(attribute: List<SocialNetwork>): String {

        val list = mutableListOf<String>()

        attribute.forEach { socialNetwork ->
            val json = mapper.createObjectNode()
            json.put("type", socialNetwork.type.name)
            json.put("link", socialNetwork.link.toString())

            list.add(json.toString())
        }
        return list.toString()
    }

    override fun convertToEntityAttribute(data: String): List<SocialNetwork> =
        mapper.readValue<List<SocialNetwork>>(data)

}