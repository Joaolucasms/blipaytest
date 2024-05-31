package com.blipaytest.blipaytest.domain.creditrating

import com.blipaytest.blipaytest.integration.weather.WeatherService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

@Service
class CreditRatingService(
    private val repository: CreditRatingRepository,
    private val weatherService: WeatherService
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun analyseCredit(name: String, age: BigDecimal, monthIncome: BigDecimal, city: String, document: String): CreditRating {
        val score = calculateScore(age, monthIncome, city)

        val creditRating = CreditRating(name, document, score, age)
        repository.save(creditRating)

        return creditRating
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun listCreditRatings(page: Int?, size: Int?): Page<CreditRating> {
        val pageRequest = PageRequest.of(page ?: 0, size ?: 100)
        val creditRatingPage = repository.findAll(pageRequest)

        return creditRatingPage
    }

    private fun calculateScore(age: BigDecimal, income: BigDecimal, city: String): BigInteger {
        val ageComponent = calculateAgeComponent(age)
        val incomeComponent = calculateIncomeComponent(income)

        val temp = findCityTemp(city)
        val tempComponent = calculateTempComponent(temp)

        val score = ageComponent + incomeComponent + tempComponent

//        toBigInteger() vai arredondar para baixo
//        isso garante que o score seja um número inteiro e que usuário com 199,9999... de score não tenham direito ao crédito
        return score.toBigInteger()
    }

    private fun calculateAgeComponent(age: BigDecimal): BigDecimal {
        return age * BigDecimal(0.5)
    }

    private fun calculateIncomeComponent(income: BigDecimal): BigDecimal {
        return (income.divide(BigDecimal(100), 6, RoundingMode.HALF_DOWN)) * BigDecimal(2)
    }

    private fun calculateTempComponent(temp: BigDecimal): BigDecimal {
        return temp * BigDecimal(5)
    }

    private fun findCityTemp(city: String): BigDecimal {
        return weatherService.findCityTemp(city)
    }
}