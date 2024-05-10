package com.br.qualrole.converter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.net.URL

@Converter
class StringListConverter(private val mapper: ObjectMapper) : AttributeConverter<List<URL>, String> {

    override fun convertToDatabaseColumn(attribute: List<URL>): String {
        return mapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String): List<URL> {
        return mapper.readValue<List<URL>>(dbData)
    }
}