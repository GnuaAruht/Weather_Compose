package com.gnua_aruht.weather_compose.data.remote.dto

import com.gnua_aruht.weather_compose.domain.model.CurrentWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDto(
    val dt : Long,
    val temp : Float,
    val pressure : Float,
    val humidity : Float,
    @SerialName("wind_speed")
    val windSpeed : Float,
    @SerialName("feels_like")
    val feelsLike : Float,
    val weather : List<WeatherDto>
) {

    fun toModel() : CurrentWeather {
        return CurrentWeather(
            dt = dt,
            temp = temp,
            pressure = pressure,
            humidity = humidity,
            windSpeed = windSpeed,
            feelsLike = feelsLike,
            weather = weather.first().toModel(),
        )
    }

}