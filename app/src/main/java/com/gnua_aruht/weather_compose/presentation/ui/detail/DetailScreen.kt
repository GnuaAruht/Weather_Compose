package com.gnua_aruht.weather_compose.presentation.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.gnua_aruht.weather_compose.R
import com.gnua_aruht.weather_compose.UserPref
import com.gnua_aruht.weather_compose.data.utils.WeatherConverter
import com.gnua_aruht.weather_compose.data.utils.selectedPressure
import com.gnua_aruht.weather_compose.data.utils.selectedTemp
import com.gnua_aruht.weather_compose.data.utils.selectedWindSpeed
import com.gnua_aruht.weather_compose.data.utils.userPrefsDataStore
import com.gnua_aruht.weather_compose.domain.model.DailyWeather
import com.gnua_aruht.weather_compose.presentation.ui.components.AppCard
import com.gnua_aruht.weather_compose.presentation.ui.components.InfoItemRow
import com.gnua_aruht.weather_compose.presentation.ui.home.format


@Composable
fun DetailScreen(
    weathers: List<DailyWeather>,
    onNavIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val userPrefs by context.userPrefsDataStore.data.collectAsState(UserPref.getDefaultInstance())

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { DetailAppBar(onNavIconClicked) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding(),
                        start = paddingValues.calculateStartPadding(LayoutDirection.Ltr) + 12.dp,
                        end = paddingValues.calculateStartPadding(LayoutDirection.Ltr) + 12.dp,
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                TomorrowWeatherCard(userPrefs,weathers.first())
                Next7DaysWeatherList(weathers.subList(1, weathers.size))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailAppBar(
    onNavIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavIconClicked) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )

}

@Composable
fun TomorrowWeatherCard(
    userPrefs: UserPref,
    dailyWeather: DailyWeather,
    modifier: Modifier = Modifier
) {

    val windSpeedValue = WeatherConverter.calculateWindSpeed(
        selectedTempUnit = userPrefs.selectedTemp,
        selectedWindSpeed = userPrefs.selectedWindSpeed,
        windSpeed = dailyWeather.windSpeed,
    )

    val pressureValue = WeatherConverter.calculatePressure(
        selectedPressureUnit = userPrefs.selectedPressure,
        pressure = dailyWeather.pressure
    )

    AppCard(modifier = modifier) {

        Column(modifier = Modifier.padding(vertical = 24.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Max),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                Image(
                    painterResource(dailyWeather.weather.icon.iconRes),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(120.dp)
                )

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.tomorrow),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "${dailyWeather.minTemp.format()} / ${dailyWeather.maxTemp.format()}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(
//                        dailyWeather.weather.description.capitalizeFirstChar,
                        dailyWeather.weather.description,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            InfoItemRow(
                windSpeed = "${windSpeedValue.format()} ${userPrefs.selectedWindSpeed.unit}",
                humidity = "${dailyWeather.humidity.format()} %",
                pressure = "${pressureValue.format()} ${userPrefs.selectedPressure.unit}",
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )

        }

    }
}

@Composable
fun ColumnScope.Next7DaysWeatherList(weathers: List<DailyWeather>) {

    Text(
        text = stringResource(R.string.in_7_days),
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
    )

    repeat(weathers.size) { index ->
        WeatherListItem(
            weather = weathers[index],
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clip(MaterialTheme.shapes.medium)
        )
    }

}

@Composable
fun WeatherListItem(
    weather: DailyWeather,
    modifier: Modifier = Modifier
) {

    ListItem(
        colors = ListItemDefaults.colors(containerColor = Color.Black.copy(alpha = 0.2f)),
        leadingContent = { Text(weather.weekDay, color = Color.White) },
        headlineContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painterResource(weather.weather.icon.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .weight(1f)
                )
                Text(
//                    weather.weather.description.capitalizeFirstChar,
                    weather.weather.description,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1.5f)
                )
            }
        },
        trailingContent = {
            Text(
                "${weather.minTemp.format()} / ${weather.maxTemp.format()}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            )
        },
        modifier = modifier
    )
}