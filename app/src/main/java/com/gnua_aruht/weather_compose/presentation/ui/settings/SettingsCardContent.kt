package com.gnua_aruht.weather_compose.presentation.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gnua_aruht.weather_compose.data.utils.PressureUnit
import com.gnua_aruht.weather_compose.data.utils.TemperatureUnit
import com.gnua_aruht.weather_compose.data.utils.TimeFormat
import com.gnua_aruht.weather_compose.data.utils.WindSpeedUnit
import com.gnua_aruht.weather_compose.presentation.theme.WeatherTheme
import com.gnua_aruht.weather_compose.presentation.ui.components.AppCard

@Composable
fun <T> SettingsContentCard(
    title: String,
    values: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable ColumnScope.(T) -> Unit,
) {

    AppCard(modifier = modifier.fillMaxWidth().wrapContentHeight()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .selectableGroup()
        ) {

            Text(
                text = title,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            )

            HorizontalDivider(
                color = Color.White,
                thickness = 0.4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            )

            values.forEach { value ->
                itemContent(value)
            }

        }
    }

}


@Composable
fun TemperatureUnitContent(
    selectedUnit: TemperatureUnit,
    onOptionSelected: (TemperatureUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsContentCard(
        title = "Temperature",
        values = TemperatureUnit.entries,
        modifier = modifier
    ) { temperatureUnit ->
        SettingOption(
            label = "${temperatureUnit.label} (${temperatureUnit.unit})",
            selected = selectedUnit == temperatureUnit,
            onOptionSelected = { onOptionSelected(temperatureUnit) },
            modifier = Modifier.padding(bottom = 6.dp)
        )
    }
}

@Composable
fun WindSpeedUnitContent(
    selectedUnit: WindSpeedUnit,
    onOptionSelected: (WindSpeedUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsContentCard(
        title = "Wind Speed",
        values = WindSpeedUnit.entries,
        modifier = modifier
    ) { windSpeedUnit ->
        SettingOption(
            label = "${windSpeedUnit.label} (${windSpeedUnit.unit})",
            selected = selectedUnit == windSpeedUnit,
            onOptionSelected = { onOptionSelected(windSpeedUnit) },
            modifier = Modifier.padding(bottom = 6.dp)
        )
    }
}


@Composable
fun PressureUnitContent(
    selectedUnit: PressureUnit,
    onOptionSelected: (PressureUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsContentCard(
        title = "Pressure",
        values = PressureUnit.entries,
        modifier = modifier
    ) { pressureUnit ->
        SettingOption(
            label = "${pressureUnit.label} (${pressureUnit.unit})",
            selected = selectedUnit == pressureUnit,
            onOptionSelected = { onOptionSelected(pressureUnit) },
            modifier = Modifier.padding(bottom = 6.dp)
        )
    }
}


@Composable
fun TimeFormatContent(
    selectedUnit: TimeFormat,
    onOptionSelected: (TimeFormat) -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsContentCard(
        title = "Time Format",
        values = TimeFormat.entries,
        modifier = modifier
    ) { timeFormat ->
        SettingOption(
            label = timeFormat.label,
            selected = selectedUnit == timeFormat,
            onOptionSelected = { onOptionSelected(timeFormat) },
            modifier = Modifier.padding(bottom = 6.dp)
        )
    }
}


@Composable
fun SettingOption(
    label: String,
    selected: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp)
            .selectable(
                selected = selected,
                onClick = { onOptionSelected() },
                role = Role.RadioButton,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        RadioButton(selected = selected, onClick = null)
    }
}


@Preview(showBackground = true)
@Composable
private fun TemperatureUnitContentPreview() {
    WeatherTheme {
        TemperatureUnitContent(
            selectedUnit = TemperatureUnit.CELSIUS,
            onOptionSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WindSpeedUnitContentPreview() {
    WeatherTheme {
        WindSpeedUnitContent(
            selectedUnit = WindSpeedUnit.MILES_PER_HOUR,
            onOptionSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PressureUnitContentPreview() {
    WeatherTheme {
        PressureUnitContent(
            selectedUnit = PressureUnit.HECTOPASCAL,
            onOptionSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeFormatContentPreview() {
    WeatherTheme {
        TimeFormatContent(
            selectedUnit = TimeFormat.HOUR_24,
            onOptionSelected = {}
        )
    }
}
