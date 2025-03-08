package com.gnua_aruht.weather_compose.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gnua_aruht.weather_compose.R


@Composable
fun InfoItemRow(
    windSpeed: String,
    humidity: String,
    pressure: String,
    modifier: Modifier = Modifier,
    horizontalArrangement : Arrangement.Horizontal = Arrangement.SpaceEvenly
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfoItem(
            value = windSpeed,
            icon = R.drawable.icon_wind_speed
        )

        InfoItem(
            value = humidity,
            icon = R.drawable.icon_humidity
        )

        InfoItem(
            value = pressure,
            icon = R.drawable.icon_pressure
        )
    }
}


@Composable
private fun InfoItem(
    value: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
        Text(
            value,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}