package com.gnua_aruht.weather_compose.data.utils

import androidx.annotation.DrawableRes
import com.gnua_aruht.weather_compose.R

object WeatherCode {
    const val CLEAR_DAY = "01d"
    const val CLEAR_NIGHT = "01n"
    const val FEW_CLOUDS_DAY = "02d"
    const val FEW_CLOUDS_NIGHT = "02n"
    const val SCATTERED_CLOUDS_DAY = "03d"
    const val SCATTERED_CLOUDS_NIGHT = "03n"
    const val BROKEN_CLOUDS_DAY = "04d"
    const val BROKEN_CLOUDS_NIGHT = "04n"
    const val SHOWER_RAIN_DAY = "09d"
    const val SHOWER_RAIN_NIGHT = "09n"
    const val RAIN_DAY = "10d"
    const val RAIN_NIGHT = "10n"
    const val THUNDER_DAY = "11d"
    const val THUNDER_NIGHT = "11n"
    const val SNOW_DAY = "13d"
    const val SNOW_NIGHT = "13n"
    const val MIST_DAY = "50d"
    const val MIST_NIGHT = "50n"
}


sealed class WeatherIcon(val iconCode: String, @DrawableRes val iconRes: Int) {

    companion object {
        fun getIconByCode(iconCode : String) : WeatherIcon {
            return when(iconCode) {
                WeatherCode.CLEAR_DAY -> ClearDay
                WeatherCode.CLEAR_NIGHT -> ClearNight
                WeatherCode.FEW_CLOUDS_DAY -> FewCloudsDay
                WeatherCode.FEW_CLOUDS_NIGHT -> FewCloudsNight
                WeatherCode.SCATTERED_CLOUDS_DAY -> ScatteredCloudsDay
                WeatherCode.SCATTERED_CLOUDS_NIGHT -> ScatteredCloudsNight
                WeatherCode.BROKEN_CLOUDS_DAY -> BrokenCloudsDay
                WeatherCode.BROKEN_CLOUDS_NIGHT -> BrokenCloudsNight
                WeatherCode.SHOWER_RAIN_DAY -> ShowerRainDay
                WeatherCode.SHOWER_RAIN_NIGHT -> ShowerRainNight
                WeatherCode.RAIN_DAY -> RainDay
                WeatherCode.RAIN_NIGHT -> RainNight
                WeatherCode.THUNDER_DAY -> ThunderDay
                WeatherCode.THUNDER_NIGHT -> ThunderNight
                WeatherCode.SNOW_DAY -> SnowDay
                WeatherCode.SNOW_NIGHT -> SnowNight
                WeatherCode.MIST_DAY -> MistDay
                WeatherCode.MIST_NIGHT -> MistNight
                else -> throw IllegalArgumentException("Unknown weather code : $iconCode")
            }
        }
    }

    /// Clear
    data object ClearDay : WeatherIcon(
        iconCode = WeatherCode.CLEAR_DAY,
        iconRes = R.drawable._01d
    )

    data object ClearNight : WeatherIcon(
        iconCode = WeatherCode.CLEAR_NIGHT,
        iconRes = R.drawable._01n
    )

    /// Few Clouds
    data object FewCloudsDay : WeatherIcon(
        iconCode = WeatherCode.FEW_CLOUDS_DAY,
        iconRes = R.drawable._02d
    )

    data object FewCloudsNight : WeatherIcon(
        iconCode = WeatherCode.FEW_CLOUDS_NIGHT,
        iconRes = R.drawable._02n
    )

    // Scattered Clouds
    data object ScatteredCloudsDay : WeatherIcon(
        iconCode = WeatherCode.SCATTERED_CLOUDS_DAY,
        iconRes = R.drawable._03d
    )

    data object ScatteredCloudsNight : WeatherIcon(
        iconCode = WeatherCode.SCATTERED_CLOUDS_NIGHT,
        iconRes = R.drawable._03n
    )

    /// Broken Clouds
    data object BrokenCloudsDay : WeatherIcon(
        iconCode = WeatherCode.BROKEN_CLOUDS_DAY,
        iconRes = R.drawable._04d
    )

    data object BrokenCloudsNight : WeatherIcon(
        iconCode = WeatherCode.BROKEN_CLOUDS_NIGHT,
        iconRes = R.drawable._04n
    )

    /// Shower rain
    data object ShowerRainDay : WeatherIcon(
        iconCode = WeatherCode.SHOWER_RAIN_DAY,
        iconRes = R.drawable._09d
    )

    data object ShowerRainNight : WeatherIcon(
        iconCode = WeatherCode.SHOWER_RAIN_NIGHT,
        iconRes = R.drawable._09n
    )

    /// Rain
    data object RainDay : WeatherIcon(
        iconCode = WeatherCode.RAIN_DAY,
        iconRes = R.drawable._10d
    )

    data object RainNight : WeatherIcon(
        iconCode = WeatherCode.RAIN_NIGHT,
        iconRes = R.drawable._10n
    )

    /// thunder
    data object ThunderDay : WeatherIcon(
        iconCode = WeatherCode.THUNDER_DAY,
        iconRes = R.drawable._11d
    )

    data object ThunderNight : WeatherIcon(
        iconCode = WeatherCode.THUNDER_NIGHT,
        iconRes = R.drawable._11n
    )

    /// Snow
    data object SnowDay : WeatherIcon(
        iconCode = WeatherCode.SNOW_DAY,
        iconRes = R.drawable._13d
    )

    data object SnowNight : WeatherIcon(
        iconCode = WeatherCode.SNOW_NIGHT,
        iconRes = R.drawable._13n
    )

    /// Mist
    data object MistDay : WeatherIcon(
        iconCode = WeatherCode.MIST_DAY,
        iconRes = R.drawable._50d
    )

    data object MistNight : WeatherIcon(
        iconCode = WeatherCode.MIST_NIGHT,
        iconRes = R.drawable._50n
    )

}