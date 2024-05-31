package com.blipaytest.blipaytest.domain.creditrating

import com.blipaytest.blipaytest.enums.CreditRatingStatus
import com.blipaytest.blipaytest.integration.weather.WeatherService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@ActiveProfiles("test")
class CreditRatingServiceTest{

    @InjectMocks
    private lateinit var creditRatingService: CreditRatingService

    @Mock
    @Autowired
    private lateinit var weatherService: WeatherService

    @Mock
    @Autowired
    private lateinit var repository: CreditRatingRepository

    @BeforeEach
    fun setup(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    @DisplayName("Should approve credit with score 200 and 18 years")
    fun analyseCreditCase1() {
        `when`(weatherService.findCityTemp("campinas")).thenReturn(BigDecimal(30))

        val creditRating = creditRatingService.analyseCredit(
            "joao",
            BigDecimal(18),
            BigDecimal(2050),
            "campinas",
            "12345678910"
        )

        verify(weatherService, times(1)).findCityTemp(anyString())
        verify(repository, times(1)).save(any())

        assertEquals(creditRating.score, BigDecimal(200).toBigInteger())
        assertEquals(creditRating.status, CreditRatingStatus.APPROVED)
    }

    @Test
    @DisplayName("Should denie credit with score 199 and 18 years")
    fun analyseCreditCase2() {
        `when`(weatherService.findCityTemp("campinas")).thenReturn(BigDecimal(30))

        val creditRating = creditRatingService.analyseCredit(
            "joao",
            BigDecimal(18),
            BigDecimal(2049),
            "campinas",
            "12345678910"
        )

        verify(weatherService, times(1)).findCityTemp(anyString())
        verify(repository, times(1)).save(any())

        assertEquals(creditRating.score, BigDecimal(199).toBigInteger())
        assertEquals(creditRating.status, CreditRatingStatus.DENIED)
    }

    @Test
    @DisplayName("Should denie credit with score 200 and 17 years")
    fun analyseCreditCase3() {
        `when`(weatherService.findCityTemp("campinas")).thenReturn(BigDecimal(30))

        val creditRating = creditRatingService.analyseCredit(
            "joao",
            BigDecimal(17),
            BigDecimal(2075),
            "campinas",
            "12345678910"
        )

        verify(weatherService, times(1)).findCityTemp(anyString())
        verify(repository, times(1)).save(any())

        assertEquals(creditRating.score, BigDecimal(200).toBigInteger())
        assertEquals(creditRating.status, CreditRatingStatus.DENIED)
    }

    @Test
    @DisplayName("Should denie credit with score 199 and 17 years")
    fun analyseCreditCase4() {
        `when`(weatherService.findCityTemp("campinas")).thenReturn(BigDecimal(30))

        val creditRating = creditRatingService.analyseCredit(
            "joao",
            BigDecimal(17),
            BigDecimal(2070),
            "campinas",
            "12345678910"
        )

        verify(weatherService, times(1)).findCityTemp(anyString())
        verify(repository, times(1)).save(any())

        assertEquals(creditRating.score, BigDecimal(199).toBigInteger())
        assertEquals(creditRating.status, CreditRatingStatus.DENIED)
    }
}