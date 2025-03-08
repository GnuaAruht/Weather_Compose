package com.gnua_aruht.weather_compose.presentation.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gnua_aruht.weather_compose.presentation.theme.WeatherTheme
import com.gnua_aruht.weather_compose.presentation.ui.components.AppCard
import com.gnua_aruht.weather_compose.presentation.ui.components.InfoItemRow

@Composable
fun InfoRowCard(
    windSpeed: String,
    humidity: String,
    pressure: String,
    modifier: Modifier = Modifier
) {

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        InfoItemRow(
            windSpeed = windSpeed,
            pressure = pressure,
            humidity = humidity,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        )
    }
}


@Preview()
@Composable
private fun InfoRowPreview() {
    WeatherTheme {
        InfoRowCard(
            windSpeed = "120 km/h",
            pressure = "11 pha",
            humidity = "60 %",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
