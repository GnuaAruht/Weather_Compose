package com.gnua_aruht.weather_compose.data.utils

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.gnua_aruht.weather_compose.domain.model.DailyWeather
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


sealed interface Route {
    @Serializable
    data object Home : Route
    @Serializable
    data class Detail(val weathers: List<DailyWeather>) : Route
    @Serializable
    data object Settings : Route
}


object CustomNavType {

    val dailyWeathers = object : NavType<List<DailyWeather>>(isNullableAllowed = false) {

        override fun get(bundle: Bundle, key: String): List<DailyWeather>? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun put(bundle: Bundle, key: String, value: List<DailyWeather>) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun parseValue(value: String): List<DailyWeather> {
            return Json.decodeFromString(value)
        }

        override fun serializeAsValue(value: List<DailyWeather>): String {
            return Uri.encode(Json.encodeToString(value))
        }

    }

}