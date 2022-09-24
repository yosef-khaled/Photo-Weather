package com.example.photoweather.core.di

import android.app.Application
import androidx.room.Room
import com.example.photoweather.core.localdatabace.AppDatabase
import com.example.photoweather.core.localdatabace.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "PhotoWeather.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }
}