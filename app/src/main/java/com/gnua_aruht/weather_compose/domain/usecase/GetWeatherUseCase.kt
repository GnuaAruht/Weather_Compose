package com.gnua_aruht.weather_compose.domain.usecase

import android.location.Location
import com.gnua_aruht.weather_compose.data.utils.DataResult
import com.gnua_aruht.weather_compose.data.utils.WeatherUnit
import com.gnua_aruht.weather_compose.domain.model.ForecastWeather
import com.gnua_aruht.weather_compose.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(location : Location,unit : WeatherUnit) : DataResult<ForecastWeather> {
        return repository.getWeather(location,unit)
    }

}