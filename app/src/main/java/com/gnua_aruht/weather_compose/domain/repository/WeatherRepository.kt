package com.gnua_aruht.weather_compose.domain.repository

import android.location.Location
import com.gnua_aruht.weather_compose.data.utils.DataResult
import com.gnua_aruht.weather_compose.data.utils.WeatherUnit
import com.gnua_aruht.weather_compose.domain.model.ForecastWeather


interface WeatherRepository {
    suspend fun getWeather(location : Location,unit : WeatherUnit) : DataResult<ForecastWeather>
    suspend fun getCurrentLocation() : DataResult<Location>
}