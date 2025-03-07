package com.gnua_aruht.weather_compose.domain.model

data class ForecastWeather(
    val lat : Double,
    val lon : Double,
    val timezone : String,
    val current : CurrentWeather,
    val hourly : List<HourlyWeather>,
    val daily : List<DailyWeather>
)