package com.gnua_aruht.weather_compose.data.utils


enum class WeatherUnit(val value : String) {
    METRIC("metric"),IMPERIAL("imperial");
}

enum class TemperatureUnit(val label: String, val unit: String) {
    CELSIUS(label = "Celsius", unit = "°C"),
    FAHRENHEIT(label = "Fahrenheit", unit = "°F");

    override fun toString(): String = unit
}

val TemperatureUnit.weatherUnit : WeatherUnit
    get() {
        return when(this) {
            TemperatureUnit.CELSIUS -> WeatherUnit.METRIC
            TemperatureUnit.FAHRENHEIT -> WeatherUnit.IMPERIAL
        }
    }

enum class WindSpeedUnit(val label: String, val unit: String) {
    METERS_PER_SECOND(label = "Meters Per Second", unit = "m/s"),
    KILOMETERS_PER_HOUR(label = "Kilometers Per Hour", unit = "km/h"),
    MILES_PER_HOUR(label = "Miles Per Hour", unit = "mph");

    override fun toString(): String = unit
}

enum class PressureUnit(val label : String,val unit : String) {
    HECTOPASCAL(label = "Hectopascal", unit = "hPa"),
    INCHESOFMERCURY(label = "Inches of Mercury", unit = "inHg"),
    MILLIMETERSOFMERCURY(label = "Millimeters of Mercury", unit = "mmHg");

    override fun toString(): String = unit
}

enum class TimeFormat(val label : String) {
    HOUR_24(label = "24-hour"),HOUR_12(label = "12-hour");
}