package com.gnua_aruht.weather_compose.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gnua_aruht.weather_compose.data.utils.Route
import com.gnua_aruht.weather_compose.presentation.ui.detail.DetailScreen
import com.gnua_aruht.weather_compose.presentation.ui.home.HomeScreen
import com.gnua_aruht.weather_compose.presentation.ui.settings.SettingsScreen


@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier
    ) {

        composable<Route.Home> {
            HomeScreen()
        }

        composable<Route.Detail> {
            DetailScreen()
        }

        composable<Route.Settings> {
            SettingsScreen()
        }

    }

}