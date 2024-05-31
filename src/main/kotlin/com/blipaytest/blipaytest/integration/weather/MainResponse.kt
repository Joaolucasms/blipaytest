package com.blipaytest.blipaytest.integration.weather

import java.math.BigDecimal

data class MainResponse(
    val temp: BigDecimal,
    val feels_like: BigDecimal,
    val temp_min: BigDecimal,
    val temp_max: BigDecimal,
    val pressure: BigDecimal,
    val humidity: BigDecimal
){
    companion object{
        val ZERO = MainResponse(
            temp = BigDecimal.ZERO,
            feels_like = BigDecimal.ZERO,
            temp_min = BigDecimal.ZERO,
            temp_max = BigDecimal.ZERO,
            pressure = BigDecimal.ZERO,
            humidity = BigDecimal.ZERO
        )
    }
}

