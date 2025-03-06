package com.gnua_aruht.weather_compose.presentation.ui.permission

import androidx.lifecycle.ViewModel
import com.gnua_aruht.weather_compose.data.utils.PermissionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(private val permissionManager: PermissionManager) : ViewModel() {

    data class PermissionState(val shouldShowRationale : Boolean? = null)

    private val _state = MutableStateFlow(PermissionState())
    val state = _state.asStateFlow()

    fun getSettingIntent() = permissionManager.createSettingsIntent()

    fun onPermissionChange() = permissionManager.checkPermissions()

    fun updateShouldShowRationale(shouldShowRationale: Boolean?) {
        _state.update {
            it.copy(shouldShowRationale = shouldShowRationale)
        }
    }


}