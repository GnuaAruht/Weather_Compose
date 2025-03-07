package com.gnua_aruht.weather_compose.data.repository

import android.location.Location
import com.gnua_aruht.weather_compose.data.remote.LocationService
import com.gnua_aruht.weather_compose.data.remote.WeatherService
import com.gnua_aruht.weather_compose.data.utils.DataResult
import com.gnua_aruht.weather_compose.data.utils.FailedToFetchDataException
import com.gnua_aruht.weather_compose.data.utils.WeatherUnit
import com.gnua_aruht.weather_compose.domain.model.ForecastWeather
import com.gnua_aruht.weather_compose.domain.repository.WeatherRepository
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val locationService: LocationService,
) : WeatherRepository {

    override suspend fun getWeather(location: Location, unit : WeatherUnit): DataResult<ForecastWeather> {
        return try {
            DataResult.Success(weatherService.getWeather(location,unit).toModel())
        } catch (e: Exception) {
            DataResult.Failed(exception = e,data = null)
        }
    }

    override suspend fun getCurrentLocation(): DataResult<Location> {
        return try {
            val location = locationService.currentLocation() ?: throw FailedToFetchDataException("Failed to get location.")
            DataResult.Success(location)
        } catch (e: Exception) {
            DataResult.Failed(exception = e)
        }
    }


}