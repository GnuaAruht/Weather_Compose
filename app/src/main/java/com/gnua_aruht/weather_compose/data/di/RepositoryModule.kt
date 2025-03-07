package com.gnua_aruht.weather_compose.data.di

import com.gnua_aruht.weather_compose.data.repository.WeatherRepositoryImpl
import com.gnua_aruht.weather_compose.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideWeatherRepository(repositoryImpl: WeatherRepositoryImpl) : WeatherRepository

}