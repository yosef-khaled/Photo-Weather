package com.example.photoweather.core.mapper

import com.example.photoweather.core.entitie.PhotoWeatherData
import com.example.photoweather.core.entitie.response.WeatherDataResponse

fun WeatherDataResponse.mapToPhotoWeatherData(photoBase64: String): PhotoWeatherData =
    PhotoWeatherData(
        id = System.currentTimeMillis(),
        ImageBase64 = photoBase64,
        temp = main.temp,
        tempMax = main.temp_max,
        tempMin = main.temp_min,
        locationName = name
    )