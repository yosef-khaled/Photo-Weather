package com.example.photoweather.core.localdatabace

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.photoweather.core.entitie.PhotoWeatherData
import io.reactivex.rxjava3.core.Single

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotoWeather(photoWeather: PhotoWeatherData)

    @Query("SELECT * FROM PhotoWeatherData")
    fun getPhotoWeatherDataList(): Single<List<PhotoWeatherData>>?
}