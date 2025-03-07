package com.gnua_aruht.weather_compose.data.di

import com.gnua_aruht.weather_compose.data.remote.LocationService
import com.gnua_aruht.weather_compose.data.remote.LocationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(locationServiceImpl: LocationServiceImpl): LocationService

}