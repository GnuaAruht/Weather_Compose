package com.gnua_aruht.weather_compose.presentation.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gnua_aruht.weather_compose.R
import com.gnua_aruht.weather_compose.data.utils.TemperatureUnit
import com.gnua_aruht.weather_compose.data.utils.TimeFormat
import com.gnua_aruht.weather_compose.domain.model.HourlyWeather
import com.gnua_aruht.weather_compose.presentation.ui.components.AppCard

@Composable
fun HourlyWeatherWithTitle(
    selectedTempUnit : TemperatureUnit,
    selectedTimeFormat : TimeFormat,
    hourlyWeathers: List<HourlyWeather>,
    onNext7DaysClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    AppCard(modifier = modifier.fillMaxWidth().wrapContentHeight()) {

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.today),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TextStyle(fontSize = 18.sp,fontWeight = FontWeight.SemiBold)
                )
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .clickable(
                            onClick = onNext7DaysClicked,
                            role = Role.Button
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.next_7_days))
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            HorizontalDivider(
                thickness = 0.4.dp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )

            HourlyWeatherGraph(
                selectedTempUnit = selectedTempUnit,
                selectedTimeFormat = selectedTimeFormat,
                values = hourlyWeathers,
                modifier = Modifier
                    .fillMaxWidth()
                    .layout { measurable, constraints ->
                        val newMaxWidth = constraints.maxWidth + (16.dp * 2).roundToPx()
                        val newConstraints = constraints.copy(maxWidth = newMaxWidth)
                        val placeable = measurable.measure(newConstraints)
                        layout(placeable.width, placeable.height) {
                            placeable.place(0, 0)
                        }
                    }
            )
        }

    }

}


@Composable
fun HourlyWeatherGraph(
    selectedTempUnit: TemperatureUnit,
    selectedTimeFormat: TimeFormat,
    values: List<HourlyWeather>,
    modifier: Modifier = Modifier
) {

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {

        item {

//            Column(modifier = Modifier.width(2800.dp)) {
            Column(modifier = Modifier.width(2880.dp)) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    values.forEach { weather ->
                        WeatherIcon(
                            icon = weather.weather.icon.iconRes,
                            value = "${weather.temp}${selectedTempUnit.unit}",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                LineChart(
                    values = values.map { it.temp },
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .height(46.dp)
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    values.forEach { value ->
                        Text(
                            text = value.formatTime(selectedTimeFormat),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun WeatherIcon(
    @DrawableRes icon: Int,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painterResource(icon),
            modifier = Modifier.size(24.dp),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall.copy(),
        )
    }
}


@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    values: List<Float>
) {
    Canvas(modifier = modifier) {

        val itemSize = size.width / values.size
        val min = values.min()
        val max = values.max()
        val dy = max - min

        val points = values.mapIndexed { index, value ->
            Offset(
                x = itemSize * index + itemSize / 2,
                y = (1 - ((value - min) / dy)) * size.height
            )
        }

        drawIntoCanvas { canvas ->
            val path = Path().apply {
                moveTo(0f, points.first().y)
                lineTo(points.first().x, points.first().y)
                for (i in 1 until points.size) {
                    val point = points[i]
                    lineTo(point.x, point.y)
                }
                lineTo(size.width, points.last().y)
            }
            canvas.drawPath(path,
                Paint().apply {
                    color = Color.White
                    strokeWidth = 2f
                    style = PaintingStyle.Stroke
                }
            )
            points.forEach { point ->
                canvas.drawCircle(
                    point,
                    10f,
                    Paint().apply {
                        strokeWidth = 2f
                        color = Color.White
                    }
                )
            }
        }
    }
}

