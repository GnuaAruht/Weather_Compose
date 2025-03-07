package com.gnua_aruht.weather_compose.data.remote

import android.location.Location

interface LocationService {
    suspend fun currentLocation() : Location?
}