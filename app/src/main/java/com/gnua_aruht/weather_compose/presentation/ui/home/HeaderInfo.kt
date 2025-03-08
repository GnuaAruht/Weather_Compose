package com.gnua_aruht.weather_compose.presentation.ui.home

import android.location.Geocoder
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gnua_aruht.weather_compose.R
import com.gnua_aruht.weather_compose.domain.model.Weather
import java.util.Locale


@Composable
fun HeaderInfo(
    lat: Double,
    lon: Double,
    temp: String,
    weather: Weather,
    formattedDate : String,
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LocationContent(
            lat = lat,
            lon = lon,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
        )
        Text(formattedDate,modifier = Modifier.padding(16.dp))
        WeatherContent(temp = temp,weather = weather)
    }
}

@Composable
private fun ColumnScope.WeatherContent(
    temp : String,
    weather: Weather,
) {

    Image(
        painterResource(weather.icon.iconRes),
        contentDescription = null,
        modifier = Modifier.size(140.dp)
    )

    Text(
//        weather.description.capitalizeFirstChar,
        weather.description,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )

    Text(
        temp,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
    )

}


@Composable
private fun LocationContent(
    lat: Double,
    lon: Double,
    modifier: Modifier = Modifier
) {
    val location by getLocation(lat = lat, lon = lon)
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.icon_location),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
        )

        Text(
            text = location,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}


@Composable
private fun getLocation(lat: Double, lon: Double): State<String> {
    val context = LocalContext.current
    return produceState(initialValue = "", lat, lon) {
        val geocoder = Geocoder(context, Locale.getDefault())
        if (Build.VERSION.SDK_INT >= 33) {
            geocoder.getFromLocation(lat, lon, 1) { addresses ->
                val address = addresses.firstOrNull()
                val place = address?.locality ?: address?.subAdminArea ?: address?.adminArea
                ?: address?.countryName
                value = place ?: ""
            }
        } else {
            val address = geocoder.getFromLocation(lat, lon, 1)?.firstOrNull()
            val place = address?.locality ?: address?.subAdminArea ?: address?.adminArea
            ?: address?.countryName
            value = place ?: ""
        }
    }
}

