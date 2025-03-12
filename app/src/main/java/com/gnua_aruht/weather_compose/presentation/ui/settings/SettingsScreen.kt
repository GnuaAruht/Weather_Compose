package com.gnua_aruht.weather_compose.presentation.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gnua_aruht.weather_compose.data.utils.selectedPressure
import com.gnua_aruht.weather_compose.data.utils.selectedTemp
import com.gnua_aruht.weather_compose.data.utils.selectedTimeFormat
import com.gnua_aruht.weather_compose.data.utils.selectedWindSpeed


@Composable
fun SettingsScreen(
    onNavIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val state by viewModel.prefsState.collectAsState()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = { SettingsAppBar(onNavIconClicked = onNavIconClicked) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding(),
                        start = paddingValues.calculateStartPadding(LayoutDirection.Ltr) + 12.dp,
                        end = paddingValues.calculateEndPadding(LayoutDirection.Ltr) + 12.dp,
                    )
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                TemperatureUnitContent(
                    selectedUnit = state.selectedTemp,
                    onOptionSelected = viewModel::updateTempUnit
                )

                WindSpeedUnitContent(
                    selectedUnit = state.selectedWindSpeed,
                    onOptionSelected = viewModel::updateWindSpeed,
                )

                PressureUnitContent(
                    selectedUnit = state.selectedPressure,
                    onOptionSelected = viewModel::updatePressure,
                )

                TimeFormatContent(
                    selectedUnit = state.selectedTimeFormat,
                    onOptionSelected = viewModel::updateTimeFormat
                )

                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsAppBar(
    onNavIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    CenterAlignedTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavIconClicked) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(
                "Settings",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )

}