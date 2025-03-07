package com.gnua_aruht.weather_compose.domain.usecase

import android.location.Location
import com.gnua_aruht.weather_compose.data.utils.DataResult
import com.gnua_aruht.weather_compose.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke() : DataResult<Location> {
        return repository.getCurrentLocation()
    }
}