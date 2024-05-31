package com.blipaytest.blipaytest.application

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import java.math.BigDecimal

data class AnalyseCreditRequest(
    @field:NotNull(message = "name is required")
    @field:NotBlank(message = "name can't be blank")
    val name: String,

    @field:Min(value = 0, message = "age must be greater than or equal to zero")
    @field:NotNull(message = "age is required")
    val age: BigDecimal,

    @field:Min(value = 0, message = "monthIncome must be greater than or equal to zero")
    @field:NotNull(message = "monthIncome is required")
    val monthIncome: BigDecimal,

    @field:NotBlank(message = "city can't be blank")
    @field:NotNull(message = "city is required")
    val city: String,

    @field:Length(min = 11, max = 11, message = "document must have 11 digits")
    @field:NotNull(message = "document is required")
    val document: String
)