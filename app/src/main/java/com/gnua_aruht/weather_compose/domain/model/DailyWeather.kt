package com.gnua_aruht.weather_compose.domain.model

import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Serializable
data class DailyWeather(
    val dt : Long,
    val pressure: Float,
    val humidity: Float,
    val windSpeed: Float,
    val weather: Weather,
    val minTemp : Float,
    val maxTemp : Float
) {

    private val dateTime : LocalDateTime
        get() {
            val instant = Instant.ofEpochSecond(dt)
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        }

    val weekDay : String
        get() {
            val formatter = DateTimeFormatter.ofPattern("EE")
            return dateTime.format(formatter)
        }
}