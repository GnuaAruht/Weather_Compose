package com.gnua_aruht.weather_compose.data.utils


sealed class DataResult<out T> {
    data class Success<T>(val data : T) : DataResult<T>()
    data class Failed<T>(val data : T? = null,val exception : Exception) : DataResult<T>()
}