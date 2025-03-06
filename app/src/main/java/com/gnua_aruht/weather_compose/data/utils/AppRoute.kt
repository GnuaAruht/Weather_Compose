package com.gnua_aruht.weather_compose.data.utils

import kotlinx.serialization.Serializable


sealed interface Route {
    @Serializable
    data object Home : Route
    @Serializable
    data object Detail : Route
    @Serializable
    data object Settings : Route
}