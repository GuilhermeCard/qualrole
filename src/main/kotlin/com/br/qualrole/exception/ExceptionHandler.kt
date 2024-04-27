package com.br.qualrole.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KLogging
import org.postgresql.util.PSQLException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpServerErrorException.InternalServerError
import java.time.LocalDateTime

@RestControllerAdvice
class ExceptionHandler(val mapper: ObjectMapper) {

    private val log = KLogging().logger()

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFoundException(ex: Exception): ResponseEntity<ErrorResponse> {
        return responseBuilder(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ResourceAlreadyExistsException::class)
    fun handleUnprocessableEntity(ex: Exception): ResponseEntity<ErrorResponse> {
        return responseBuilder(ex.message, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(InternalServerError::class, PSQLException::class)
    fun handleInternalServerError(ex: Exception): ResponseEntity<ErrorResponse> {
        log.error { ex.message }
        return responseBuilder("The server encountered an unexpected error.", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun responseBuilder(message: String?, status: HttpStatus): ResponseEntity<ErrorResponse> {
        val errors =
            message?.runCatching { mapper.readValue(this) as List<String> }?.getOrNull() ?: listOf(message)
        return ResponseEntity(ErrorResponse(status, errors), status)
    }

}

data class ErrorResponse(
    val status: HttpStatus,
    val errors: List<String?>,
    val occurredAt: LocalDateTime = LocalDateTime.now()
)