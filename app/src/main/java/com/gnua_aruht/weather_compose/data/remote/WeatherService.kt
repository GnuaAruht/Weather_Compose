package com.gnua_aruht.weather_compose.data.remote

import android.location.Location
import com.gnua_aruht.weather_compose.data.remote.dto.WeatherResponseDto
import com.gnua_aruht.weather_compose.data.utils.WeatherUnit

interface WeatherService {
    suspend fun getWeather(location: Location, unit : WeatherUnit) : WeatherResponseDto
}