package com.gnua_aruht.weather_compose.presentation.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gnua_aruht.weather_compose.UserPref
import com.gnua_aruht.weather_compose.data.utils.PressureUnit
import com.gnua_aruht.weather_compose.data.utils.TemperatureUnit
import com.gnua_aruht.weather_compose.data.utils.TimeFormat
import com.gnua_aruht.weather_compose.data.utils.WindSpeedUnit
import com.gnua_aruht.weather_compose.data.utils.userPrefsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {

    val prefsState = context.userPrefsDataStore.data.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = UserPref.getDefaultInstance()
    )

    fun updateTempUnit(tempUnit: TemperatureUnit) {
        viewModelScope.launch {
            context.userPrefsDataStore.updateData { prefs ->
                prefs.toBuilder().setTempUnit(tempUnit.ordinal).build()
            }
        }
    }

    fun updateWindSpeed(windSpeedUnit: WindSpeedUnit) {
        viewModelScope.launch {
            context.userPrefsDataStore.updateData { prefs ->
                prefs.toBuilder().setWindSpeedUnit(windSpeedUnit.ordinal).build()
            }
        }
    }

    fun updatePressure(pressureUnit: PressureUnit) {
        viewModelScope.launch {
            context.userPrefsDataStore.updateData { prefs ->
                prefs.toBuilder().setPressureUnit(pressureUnit.ordinal).build()
            }
        }
    }

    fun updateTimeFormat(timeFormat: TimeFormat) {
        viewModelScope.launch {
            context.userPrefsDataStore.updateData { prefs ->
                prefs.toBuilder().setTimeUnit(timeFormat.ordinal).build()
            }
        }
    }

}