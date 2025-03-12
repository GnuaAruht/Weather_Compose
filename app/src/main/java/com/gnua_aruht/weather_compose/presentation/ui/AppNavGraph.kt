package com.gnua_aruht.weather_compose.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.gnua_aruht.weather_compose.data.utils.CustomNavType
import com.gnua_aruht.weather_compose.data.utils.Route
import com.gnua_aruht.weather_compose.domain.model.DailyWeather
import com.gnua_aruht.weather_compose.presentation.ui.detail.DetailScreen
import com.gnua_aruht.weather_compose.presentation.ui.home.HomeScreen
import com.gnua_aruht.weather_compose.presentation.ui.settings.SettingsScreen
import kotlin.reflect.typeOf


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
            HomeScreen(
                onMenuClicked = { navController.navigate(Route.Settings) },
                onNext7DaysClicked = { navController.navigate(Route.Detail(it)) }
            )
        }

        composable<Route.Detail>(typeMap = mapOf(typeOf<List<DailyWeather>>() to CustomNavType.dailyWeathers)) {
            val arguments = it.toRoute<Route.Detail>()
            DetailScreen(
                onNavIconClicked = { navController.navigateUp() },
                weathers = arguments.weathers
            )
        }

        composable<Route.Settings> {
            SettingsScreen(onNavIconClicked = { navController.navigateUp() })
        }

    }

}