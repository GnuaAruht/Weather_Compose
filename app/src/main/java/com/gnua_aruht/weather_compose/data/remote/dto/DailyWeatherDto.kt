package com.gnua_aruht.weather_compose.data.remote.dto

import com.gnua_aruht.weather_compose.domain.model.DailyWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DailyWeatherDto(
    val dt : Long,
    val pressure: Float,
    val humidity: Float,
    @SerialName("wind_speed")
    val windSpeed: Float,
    val weather: List<WeatherDto>,
    val temp: DailyTempDto
) {
    fun toModel() : DailyWeather {
        return DailyWeather(
            dt = dt,
            pressure = pressure,
            humidity = humidity,
            windSpeed = windSpeed,
            weather = weather.first().toModel(),
            minTemp = temp.min,
            maxTemp = temp.max,
        )
    }
}


@Serializable
data class DailyTempDto(
    val min: Float,
    val max: Float,
)



