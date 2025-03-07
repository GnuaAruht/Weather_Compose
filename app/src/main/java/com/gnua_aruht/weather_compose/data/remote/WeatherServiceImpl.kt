package com.gnua_aruht.weather_compose.data.remote

import android.location.Location
import com.gnua_aruht.weather_compose.data.remote.dto.WeatherResponseDto
import com.gnua_aruht.weather_compose.data.utils.ApiConsts
import com.gnua_aruht.weather_compose.data.utils.WeatherUnit
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject


class WeatherServiceImpl @Inject constructor(private val client : HttpClient) : WeatherService {

    override suspend fun getWeather(location: Location, unit : WeatherUnit) : WeatherResponseDto {
        val response : HttpResponse = client.get(ApiConsts.ONE_CALL) {
            parameter("lat",location.latitude)
            parameter("lon",location.longitude)
            parameter("exclude","minutely,alerts")
            parameter("appid",ApiConsts.API_KEY)
            parameter("units",unit.value)
        }
        return response.body<WeatherResponseDto>()
    }

}