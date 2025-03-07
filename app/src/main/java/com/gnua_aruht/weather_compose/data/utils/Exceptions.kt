package com.gnua_aruht.weather_compose.data.utils


class FailedToFetchDataException(override val message: String? = "Failed to load data.") : Exception()

class LocationServiceDisableException : Exception("Location service is not enabled.")