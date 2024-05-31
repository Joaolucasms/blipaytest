package com.blipaytest.blipaytest.application

data class ExceptionResponse(
    val messages: List<String?>,
    val statusCode: Int
)
