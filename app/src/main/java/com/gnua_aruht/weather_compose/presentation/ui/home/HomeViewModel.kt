package com.gnua_aruht.weather_compose.presentation.ui.home

import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gnua_aruht.weather_compose.UserPref
import com.gnua_aruht.weather_compose.data.utils.DataResult
import com.gnua_aruht.weather_compose.data.utils.TemperatureUnit
import com.gnua_aruht.weather_compose.data.utils.WeatherUnit
import com.gnua_aruht.weather_compose.data.utils.selectedTemp
import com.gnua_aruht.weather_compose.data.utils.userPrefsDataStore
import com.gnua_aruht.weather_compose.data.utils.weatherUnit
import com.gnua_aruht.weather_compose.domain.model.ForecastWeather
import com.gnua_aruht.weather_compose.domain.usecase.GetCurrentLocationUseCase
import com.gnua_aruht.weather_compose.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getWeather: GetWeatherUseCase,
    private val getCurrentLocation: GetCurrentLocationUseCase,
) : ViewModel() {

    data class UIState(
        val userPerf: UserPref = UserPref.getDefaultInstance(),
        val isRefreshing: Boolean = false,
        val data: ForecastWeather? = null,
        val exception: Exception? = null
    ) {
        val tempUnit: TemperatureUnit
            get() = userPerf.selectedTemp

        val weatherUnit: WeatherUnit
            get() = tempUnit.weatherUnit
    }

    private val _state = MutableStateFlow(UIState())
    val state: StateFlow<UIState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            context.userPrefsDataStore.data.collect { userPerfs ->
                if(_state.value.tempUnit != userPerfs.selectedTemp || _state.value.data == null) {
                    loadWeatherData(userPerfs.selectedTemp.weatherUnit)
                }
                _state.update { it.copy(userPerf = userPerfs) }
            }
        }
    }

    fun refreshWeatherData() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            delay(1200) // add delay for dummy
            loadWeatherData(_state.value.weatherUnit)
        }
    }

    private suspend fun loadWeatherData(weatherUnit: WeatherUnit) {
        when (val locResult = getCurrentLocation()) {
            is DataResult.Success -> { getWeatherData(locResult.data,weatherUnit) }
            is DataResult.Failed -> {
                _state.update {
                    it.copy(
                        isRefreshing = false,
                        exception = locResult.exception
                    )
                }
            }
        }
    }


    private suspend fun getWeatherData(location: Location, weatherUnit: WeatherUnit) {
        when (val result = getWeather.invoke(location, weatherUnit)) {
            is DataResult.Success -> {
                _state.update {
                    it.copy(
                        data = result.data,
                        isRefreshing = false,
                        exception = null
                    )
                }
            }

            is DataResult.Failed -> {
                _state.update {
                    it.copy(
                        data = result.data,
                        isRefreshing = false,
                        exception = result.exception
                    )
                }
            }
        }
    }

}