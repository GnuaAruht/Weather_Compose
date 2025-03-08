package com.gnua_aruht.weather_compose.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gnua_aruht.weather_compose.R
import com.gnua_aruht.weather_compose.UserPref
import com.gnua_aruht.weather_compose.data.utils.WeatherConverter
import com.gnua_aruht.weather_compose.data.utils.selectedPressure
import com.gnua_aruht.weather_compose.data.utils.selectedTemp
import com.gnua_aruht.weather_compose.data.utils.selectedTimeFormat
import com.gnua_aruht.weather_compose.data.utils.selectedWindSpeed
import com.gnua_aruht.weather_compose.domain.model.DailyWeather
import com.gnua_aruht.weather_compose.domain.model.ForecastWeather
import com.gnua_aruht.weather_compose.presentation.ui.components.PullToRefreshLayout
import java.lang.Exception
import java.util.Locale


fun Float.format(decimalPlace: Int = 1): String {
    return String.format(Locale.getDefault(), "%.${decimalPlace}f", this)
}

@Composable
fun HomeScreen(
    onMenuClicked: () -> Unit,
    onNext7DaysClicked: (List<DailyWeather>) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { HomeAppBar(onMenuClicked = onMenuClicked) },
        content = {

            val contentModifier = Modifier.padding(it)

            if (state.data != null) {
                val weatherData = state.data!!
                DataContent(
                    weather = weatherData,
                    userPrefs = state.userPerf,
                    onNext7DaysClicked = { onNext7DaysClicked(weatherData.daily) },
                    isRefreshing = state.isRefreshing,
                    onRefresh = { viewModel.refreshWeatherData() },
                    modifier = contentModifier
                )
            } else {
                if (state.exception != null) {
                    ErrorContent(
                        exception = state.exception!!,
                        modifier = contentModifier
                    )
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeAppBar(
    onMenuClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        actions = {

            Image(
                painterResource(R.drawable.icon_settings),
                contentDescription = "settings",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(36.dp)
                    .clickable(role = Role.Button) { onMenuClicked() }
            )

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataContent(
    weather: ForecastWeather,
    userPrefs: UserPref,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onNext7DaysClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val currentWeather = weather.current
    val hourlyWeathers = weather.hourly

    val windSpeedValue = WeatherConverter.calculateWindSpeed(
        selectedTempUnit = userPrefs.selectedTemp,
        selectedWindSpeed = userPrefs.selectedWindSpeed,
        windSpeed = currentWeather.windSpeed,
    )

    val pressureValue = WeatherConverter.calculatePressure(
        selectedPressureUnit = userPrefs.selectedPressure,
        pressure = currentWeather.pressure
    )

    PullToRefreshLayout(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            HeaderInfo(
                lat = weather.lat,
                lon = weather.lon,
                temp = "${currentWeather.temp.format()}${userPrefs.selectedTemp}",
                weather = currentWeather.weather,
                formattedDate = currentWeather.formatTitleDate(),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            InfoRowCard(
                windSpeed = "${windSpeedValue.format()} ${userPrefs.selectedWindSpeed}",
                humidity = "${currentWeather.humidity} %",
                pressure = "${pressureValue.format()} ${userPrefs.selectedPressure}",
                modifier = Modifier.fillMaxWidth()
            )

            HourlyWeatherWithTitle(
                selectedTempUnit = userPrefs.selectedTemp,
                selectedTimeFormat = userPrefs.selectedTimeFormat,
                hourlyWeathers = hourlyWeathers,
                onNext7DaysClicked = onNext7DaysClicked,
                modifier = Modifier.fillMaxWidth()
            )

        }

    }

}


@Composable
fun ErrorContent(
    exception: Exception,
    modifier: Modifier = Modifier
) {

    Text(
        "Error content",
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)
    )
}


