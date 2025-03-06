package com.gnua_aruht.weather_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.gnua_aruht.weather_compose.data.utils.PermissionManager
import com.gnua_aruht.weather_compose.presentation.theme.WeatherTheme
import com.gnua_aruht.weather_compose.presentation.ui.WeatherApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                val permissionState by permissionManager.state.collectAsState()
                WeatherApp(
                    permissionState = permissionState,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        permissionManager.checkPermissions()
    }
}
