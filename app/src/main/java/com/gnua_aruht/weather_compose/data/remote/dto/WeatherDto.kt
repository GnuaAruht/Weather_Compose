package com.gnua_aruht.weather_compose.data.remote.dto

import com.gnua_aruht.weather_compose.domain.model.Weather
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
) {
    fun toModel(): Weather {
        return Weather(
            id = id,
            main = main,
            description = description,
            iconCode = icon
        )
    }
}