package com.br.qualrole.converter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class IntListConverter(private val mapper: ObjectMapper) : AttributeConverter<List<Int>, String> {

    override fun convertToDatabaseColumn(attribute: List<Int>): String {
        return mapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String): List<Int> {
        return mapper.readValue<List<Int>>(dbData)
    }
}