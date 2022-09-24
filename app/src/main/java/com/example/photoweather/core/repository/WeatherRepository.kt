package com.example.photoweather.core.repository

import com.example.photoweather.core.entitie.PhotoWeatherData
import com.example.photoweather.core.localdatabace.WeatherDao
import com.example.photoweather.core.mapper.mapToPhotoWeatherData
import com.example.photoweather.core.network.WeatherClient
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherClient: WeatherClient,
    private val weatherDao: WeatherDao
) {

    fun fetchWeatherData(photoBase64: String, lat: Double, lon: Double): Single<PhotoWeatherData> =
        weatherClient.fetchWeatherData(lat, lon).map { it.mapToPhotoWeatherData(photoBase64) }.doOnSuccess {
            insertPhotoWeatherData(it)
        }

    fun fetchPhotoWeatherDataList() : Single<List<PhotoWeatherData>> =
        weatherDao.getPhotoWeatherDataList() ?: Single.just(listOf())

    fun insertPhotoWeatherData(photoWeatherData: PhotoWeatherData){
        weatherDao.insertPhotoWeather(photoWeatherData)
    }

}