package com.example.photoweather.core.di

import com.example.photoweather.core.localdatabace.WeatherDao
import com.example.photoweather.core.network.WeatherClient
import com.example.photoweather.core.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideWeatherRepository(
        weatherClient: WeatherClient,
        weatherDao: WeatherDao
    ): WeatherRepository {
        return WeatherRepository(weatherClient,weatherDao)
    }

}