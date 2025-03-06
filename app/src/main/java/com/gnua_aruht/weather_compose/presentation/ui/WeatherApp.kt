package com.gnua_aruht.weather_compose.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gnua_aruht.weather_compose.presentation.ui.permission.PermissionScreen


@Composable
fun WeatherApp(
    permissionState: Boolean,
    modifier: Modifier = Modifier,
) {

    AnimatedContent(
        targetState = permissionState,
        label = "permission_check_content",
        modifier = modifier,
    ) { isGranted ->
        if (isGranted) {
            AppNavGraph(modifier = Modifier.fillMaxSize())
        } else {
            PermissionScreen(modifier = Modifier.fillMaxSize())
        }
    }
}