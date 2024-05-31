package com.blipaytest.blipaytest.application

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CreditRatingControllerTest{
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper


    @Test
    @DisplayName("Should return Bad Request for empty body")
    fun analyseCreditCase1() {
        val response = mockMvc.perform(post(CREDIT_RATING_CONTROLLER_BASE_URL))
        response.andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("Should return Bad Request for invalid age")
    fun analyseCreditCase2() {
        val response = mockMvc.perform(
            post(CREDIT_RATING_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(provideAnalyseCreditRequestWithInvalidAge()))
        )

        response.andExpect(status().isBadRequest)
        response.andExpect(content().json(mapper.writeValueAsString(provideExceptionResponseForInvalidAge())))
    }

    @Test
    @DisplayName("Should return Bad Request for invalid monthIncome")
    fun analyseCreditCase3() {
        val response = mockMvc.perform(
            post(CREDIT_RATING_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(provideAnalyseCreditRequestWithInvalidMonthIncome()))
        )

        response.andExpect(status().isBadRequest)
        response.andExpect(content().json(mapper.writeValueAsString(provideExceptionResponseForInvalidMonthIncome())))
    }

    @Test
    @DisplayName("Should return Bad Request for invalid document")
    fun analyseCreditCase4() {
        val response = mockMvc.perform(
            post(CREDIT_RATING_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(provideAnalyseCreditRequestWithInvalidDocument()))
        )

        response.andExpect(status().isBadRequest)
        response.andExpect(content().json(mapper.writeValueAsString(provideExceptionResponseForInvalidDocument())))
    }

    @Test
    @DisplayName("Should return Bad Request for invalid document 2")
    fun analyseCreditCase5() {
        val response = mockMvc.perform(
            post(CREDIT_RATING_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(provideAnalyseCreditRequestWithInvalidDocument2()))
        )

        response.andExpect(status().isBadRequest)
        response.andExpect(content().json(mapper.writeValueAsString(provideExceptionResponseForInvalidDocument2())))
    }

    @Test
    @DisplayName("Should return Bad Request for invalid city")
    fun analyseCreditCase6() {
        val response = mockMvc.perform(
            post(CREDIT_RATING_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(provideAnalyseCreditRequestWithInvalidCity()))
        )

        response.andExpect(status().isBadRequest)
        response.andExpect(content().json(mapper.writeValueAsString(provideExceptionResponseForInvalidCity())))
    }

    @Test
    @DisplayName("Should return Bad Request for invalid name")
    fun analyseCreditCase7() {
        val response = mockMvc.perform(
            post(CREDIT_RATING_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(provideAnalyseCreditRequestWithInvalidName()))
        )

        response.andExpect(status().isBadRequest)
        response.andExpect(content().json(mapper.writeValueAsString(provideExceptionResponseForInvalidName())))
    }

    private fun provideAnalyseCreditRequestWithInvalidAge(): AnalyseCreditRequest {
        return AnalyseCreditRequest(
            name = "joao",
            age = BigDecimal(-1),
            monthIncome = BigDecimal.TEN,
            city = "Campinas",
            document = "12345678910"
        )
    }

    private fun provideExceptionResponseForInvalidAge(): ExceptionResponse {
        return ExceptionResponse(
            listOf("age must be greater than or equal to zero"),
            HttpStatus.BAD_REQUEST.value()
        )
    }

    private fun provideAnalyseCreditRequestWithInvalidMonthIncome(): AnalyseCreditRequest {
        return AnalyseCreditRequest(
            name ="joao",
            age = BigDecimal(26),
            monthIncome = BigDecimal(-1),
            city = "Campinas",
            document = "12345678910"
        )
    }

    private fun provideExceptionResponseForInvalidMonthIncome(): ExceptionResponse {
        return ExceptionResponse(
            listOf("monthIncome must be greater than or equal to zero"),
            HttpStatus.BAD_REQUEST.value()
        )
    }

    private fun provideAnalyseCreditRequestWithInvalidDocument(): AnalyseCreditRequest {
        return AnalyseCreditRequest(
            name ="joao",
            age = BigDecimal.TEN,
            monthIncome = BigDecimal.TEN,
            city = "Campinas",
            document = "123456789"
        )
    }

    private fun provideExceptionResponseForInvalidDocument(): ExceptionResponse {
        return ExceptionResponse(
            listOf("document must have 11 digits"),
            HttpStatus.BAD_REQUEST.value()
        )
    }

    private fun provideAnalyseCreditRequestWithInvalidDocument2(): AnalyseCreditRequest {
        return AnalyseCreditRequest(
            name ="joao",
            age = BigDecimal.TEN,
            monthIncome = BigDecimal.TEN,
            city = "Campinas",
            document = "123456789123"
        )
    }

    private fun provideExceptionResponseForInvalidDocument2(): ExceptionResponse {
        return ExceptionResponse(
            listOf("document must have 11 digits"),
            HttpStatus.BAD_REQUEST.value()
        )
    }

    private fun provideAnalyseCreditRequestWithInvalidCity(): AnalyseCreditRequest {
        return AnalyseCreditRequest(
            name ="joao",
            age = BigDecimal.TEN,
            monthIncome = BigDecimal.TEN,
            city = "",
            document = "12345678912"
        )
    }

    private fun provideExceptionResponseForInvalidCity(): ExceptionResponse {
        return ExceptionResponse(
            listOf("city can't be blank"),
            HttpStatus.BAD_REQUEST.value()
        )
    }

    private fun provideAnalyseCreditRequestWithInvalidName(): AnalyseCreditRequest {
        return AnalyseCreditRequest(
            name ="",
            age = BigDecimal.TEN,
            monthIncome = BigDecimal.TEN,
            city = "campinas",
            document = "12345678912"
        )
    }

    private fun provideExceptionResponseForInvalidName(): ExceptionResponse {
        return ExceptionResponse(
            listOf("name can't be blank"),
            HttpStatus.BAD_REQUEST.value()
        )
    }

    companion object{
        private const val CREDIT_RATING_CONTROLLER_BASE_URL = "/api/v1/credit-rating"
    }
}