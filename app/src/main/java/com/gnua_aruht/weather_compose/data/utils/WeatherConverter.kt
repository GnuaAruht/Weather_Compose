package com.gnua_aruht.weather_compose.data.utils

object WeatherConverter {

    fun calculateWindSpeed(
        selectedTempUnit: TemperatureUnit,
        selectedWindSpeed: WindSpeedUnit,
        windSpeed: Float,
    ): Float {

        return when (selectedTempUnit) {
            TemperatureUnit.CELSIUS -> {
                when (selectedWindSpeed) {
                    WindSpeedUnit.METERS_PER_SECOND -> windSpeed
                    WindSpeedUnit.KILOMETERS_PER_HOUR -> windSpeed * 3.6f
                    WindSpeedUnit.MILES_PER_HOUR -> windSpeed * 2.23694f
                }
            }

            TemperatureUnit.FAHRENHEIT -> {
                when (selectedWindSpeed) {
                    WindSpeedUnit.METERS_PER_SECOND -> windSpeed * 0.44704f
                    WindSpeedUnit.KILOMETERS_PER_HOUR -> windSpeed * 1.60934f
                    WindSpeedUnit.MILES_PER_HOUR -> windSpeed
                }
            }
        }
    }

    fun calculatePressure(selectedPressureUnit: PressureUnit, pressure: Float): Float {
        return when (selectedPressureUnit) {
            PressureUnit.HECTOPASCAL -> pressure
            PressureUnit.INCHESOFMERCURY -> pressure * 0.02953f
            PressureUnit.MILLIMETERSOFMERCURY -> pressure * 0.75006f
        }
    }

}