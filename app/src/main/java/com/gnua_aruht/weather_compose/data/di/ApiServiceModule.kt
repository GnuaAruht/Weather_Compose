package com.gnua_aruht.weather_compose.data.di

import com.gnua_aruht.weather_compose.data.remote.WeatherService
import com.gnua_aruht.weather_compose.data.remote.WeatherServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ApiServiceModule {

    @Binds
    abstract fun binApiService(weatherService : WeatherServiceImpl) : WeatherService

}
