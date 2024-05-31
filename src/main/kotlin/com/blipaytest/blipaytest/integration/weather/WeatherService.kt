package com.blipaytest.blipaytest.integration.weather

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.math.BigDecimal

@Service
class WeatherService {
    @Value("\${weather-api.url}")
    private lateinit var weatherApiBaseUrl: String

    @Value("\${weather-api.key}")
    private lateinit var weatherApiKey: String

    fun findCityTemp(city: String): BigDecimal {
        val weatherResponse = findCityWheatherInfo(city)
        val celsiusTemp = convertKelvinToCelsius(weatherResponse?.main?.temp ?: BigDecimal.ZERO)

        // o enunciado não definiu um comportamento para quando a temperatura da cidade não for encontrada
        // sempre que a temperatura não fora encontrada será considerada O K
        return celsiusTemp
    }

    private fun convertKelvinToCelsius(kelvinValue: BigDecimal): BigDecimal {
        return kelvinValue.minus(BigDecimal(273.15))
    }

    private fun findCityWheatherInfo(city: String): WeatherResponse? {
        val uri = UriComponentsBuilder.fromHttpUrl("$weatherApiBaseUrl/weather")
        uri.queryParam("q", city)
        uri.queryParam("appid", weatherApiKey)

        try {
            val weatherResponse = rest.exchange<WeatherResponse>(
                uri.toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                ParameterizedTypeReference.forType(WeatherResponse::class.java)
            )

            return weatherResponse.body
        }catch (e: Exception){
//            o enunciado não definiu um comportamento para quando não for possível buscar a temperatura da cidade
//            o comportamento escolhido adotado aqui é considerar que a temperatura é 0 K
            return WeatherResponse.ZERO
        }
    }

    companion object {
        private val rest = RestTemplate()
    }
}
