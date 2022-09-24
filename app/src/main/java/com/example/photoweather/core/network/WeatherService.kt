/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.photoweather.core.network

import com.example.photoweather.core.entitie.response.WeatherDataResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

  @GET("data/2.5/weather")
  fun fetchWeatherData(
    @Query("lat") lat: Double,
    @Query("lon") lon: Double,
    @Query("appid") appid: String = "ca2059bb8e1b1f43efd1cd068316260a",
  ): Single<WeatherDataResponse>
}
