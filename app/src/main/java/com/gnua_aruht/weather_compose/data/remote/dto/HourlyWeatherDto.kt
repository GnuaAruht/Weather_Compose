package com.gnua_aruht.weather_compose.data.remote.dto

import com.gnua_aruht.weather_compose.domain.model.HourlyWeather
import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeatherDto(
    val temp: Float,
    val dt : Long,
    val weather : List<WeatherDto>,
) {

    fun toModel() : HourlyWeather {
        return HourlyWeather(
            dt = dt,
            temp = temp,
            weather = weather.first().toModel()
        )
    }
}