package com.blipaytest.blipaytest.infra

import com.blipaytest.blipaytest.application.ExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(listOf(ex.localizedMessage), HttpStatus.BAD_REQUEST.value())
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(ex.allErrors.map { it.defaultMessage }, HttpStatus.BAD_REQUEST.value())
        return ResponseEntity.badRequest().body(response)
    }
}