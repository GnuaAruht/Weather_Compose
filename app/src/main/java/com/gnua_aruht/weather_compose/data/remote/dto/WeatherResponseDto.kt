package com.gnua_aruht.weather_compose.data.remote.dto

import com.gnua_aruht.weather_compose.domain.model.ForecastWeather
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val current: CurrentWeatherDto,
    val hourly: List<HourlyWeatherDto>,
    val daily: List<DailyWeatherDto>
) {

    fun toModel(): ForecastWeather {
        return ForecastWeather(
            lat = lat,
            lon = lon,
            timezone = timezone,
            current = current.toModel(),
            hourly = hourly.map { it.toModel() }.toList(),
            daily = daily.map { it.toModel() }.toList(),
        )
    }

}