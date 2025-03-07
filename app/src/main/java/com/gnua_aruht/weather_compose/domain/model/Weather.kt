package com.gnua_aruht.weather_compose.domain.model

import com.gnua_aruht.weather_compose.data.utils.WeatherIcon
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val iconCode: String,
) {

    val icon : WeatherIcon
        get() = WeatherIcon.getIconByCode(iconCode)

    companion object {
        val dummy = Weather(
            id = 500,
            main = "Rain",
            description = "light rain",
            iconCode = "10n"
        )
    }

}