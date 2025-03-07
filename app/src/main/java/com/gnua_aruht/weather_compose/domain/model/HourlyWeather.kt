package com.gnua_aruht.weather_compose.domain.model

import com.gnua_aruht.weather_compose.data.utils.TimeFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class HourlyWeather(
    val dt : Long,
    val temp: Float,
    val weather: Weather
) {

    private val dateTime : LocalDateTime
        get() {
            val instant = Instant.ofEpochSecond(dt)
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        }

    fun formatTime(timeFormat : TimeFormat) : String {
        return when(timeFormat) {
            TimeFormat.HOUR_12 -> {
                val formatter = DateTimeFormatter.ofPattern("hh:mm a")
                dateTime.format(formatter)
            }
            TimeFormat.HOUR_24 -> {
                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                dateTime.format(formatter)
            }
        }
    }

}