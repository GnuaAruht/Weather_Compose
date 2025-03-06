package com.gnua_aruht.weather_compose.data.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PermissionManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {

        val APP_PERMISSIONS = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)

        fun shouldShowRationale(activity: Activity): Boolean {
            return APP_PERMISSIONS.any { permission ->
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    permission
                )
            }
        }

    }

    private val _state = MutableStateFlow(hasAllPermissions())
    val state = _state.asStateFlow()

    fun checkPermissions() {
        _state.value = hasAllPermissions()
    }

    private fun hasAllPermissions() = APP_PERMISSIONS.all(::hasPermission)

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun createSettingsIntent(): Intent {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.fromParts("package", context.packageName, null)
        }
        return intent
    }

}