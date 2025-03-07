package com.gnua_aruht.weather_compose.domain.model

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


data class CurrentWeather(
    val dt: Long,
    val temp: Float,
    val pressure: Float,
    val humidity: Float,
    val windSpeed: Float,
    val feelsLike: Float,
    val weather: Weather
) {

    fun formatTitleDate() : String {

        val timeInMillis = dt * 1000
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis

        val format = SimpleDateFormat("dd MMM", Locale.getDefault())
        val formattedDate = format.format(calendar.time)

        return "${getDayAgo(timeInMillis)}, $formattedDate"
    }

    private fun getDayAgo(timeInMillis : Long) : String {

        val calendar = Calendar.getInstance().apply {
            setTimeInMillis(timeInMillis)
        }
        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }

        return when {
            calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) -> "Today"

            calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) &&
            calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR) -> "Yesterday"

            else -> {
                val daysAgo = today.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR)
                "$daysAgo days ago"
            }
        }

    }

    private val dateTime: LocalDateTime
        get() {
            val instant = Instant.ofEpochSecond(dt)
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        }

    val formattedDate: String
        get() {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return dateTime.format(formatter)
        }

}