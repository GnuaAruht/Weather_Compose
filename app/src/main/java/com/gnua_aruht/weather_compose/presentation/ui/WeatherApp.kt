package com.gnua_aruht.weather_compose.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.gnua_aruht.weather_compose.presentation.ui.permission.PermissionScreen
import java.util.Calendar

fun checkIfDayTime(): Boolean {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return hour in 6..18
}

private val DAY_COLORS = listOf(Color(0xFF44B0FF), Color(0xFF104197))
private val NIGHT_COLORS = listOf(Color(0xFF134CB5),Color(0xFF08244F))


@Composable
fun WeatherApp(
    permissionState: Boolean,
    modifier: Modifier = Modifier,
) {

    val isDayTime = remember { checkIfDayTime() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                val brush = Brush.linearGradient(
                    colors = if(isDayTime) DAY_COLORS else NIGHT_COLORS,
                    start = Offset(size.width, 0f),
                    end = Offset(0f, size.height)
                )
                drawRect(
                    brush = brush,
                    topLeft = Offset.Zero,
                    size = size
                )
            }
    ) {
        AnimatedContent(
            targetState = permissionState,
            label = "permission_check_content",
            modifier = Modifier.fillMaxSize(),
        ) { isGranted ->
            if (isGranted) {
                AppNavGraph(modifier = Modifier.fillMaxSize())
            } else {
                PermissionScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }

}