package com.example.photoweather.ui.fragment.camera

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.example.photoweather.core.bases.BaseViewModel
import com.example.photoweather.core.entitie.PhotoWeatherData
import com.example.photoweather.core.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val weatherUseCase: WeatherRepository
) : BaseViewModel() {

    var photo: Bitmap? = null
    val screenState by lazy { MutableLiveData<CameraScreenState>() }

    fun fetchWeatherData(photoBase64: String, lat: Double, lon: Double) {
        screenState.postValue(CameraScreenState.Loading)
        weatherUseCase.fetchWeatherData(photoBase64, lat, lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                screenState.postValue(CameraScreenState.Success(it))
            }, {
                screenState.postValue(CameraScreenState.Error(it.message ?: ""))
            })
            .also { addDisposable(it) }
    }

}

sealed class CameraScreenState {
    object Loading : CameraScreenState()
    data class Success(val photoWeatherData: PhotoWeatherData) : CameraScreenState()
    data class Error(val errorMessage: String) : CameraScreenState()
}