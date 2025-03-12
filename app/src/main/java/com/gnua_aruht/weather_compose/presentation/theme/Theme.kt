package com.gnua_aruht.weather_compose.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val AppColorScheme = darkColorScheme(
    primary = Color.White,
    background = Color.Transparent,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun WeatherTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}