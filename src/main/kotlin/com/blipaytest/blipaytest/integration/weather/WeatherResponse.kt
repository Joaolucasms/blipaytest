package com.blipaytest.blipaytest.integration.weather

data class WeatherResponse(val main: MainResponse?){
    companion object{
        val ZERO = WeatherResponse(MainResponse.ZERO)
    }
}
