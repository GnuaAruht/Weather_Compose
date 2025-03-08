package com.gnua_aruht.weather_compose.presentation.ui.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gnua_aruht.weather_compose.domain.model.DailyWeather


@Composable
fun DetailScreen(
    weathers: List<DailyWeather>,
    onNavIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(modifier = modifier.fillMaxSize()) {
        Text(
            "Detail Screen",
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        )

    }

}