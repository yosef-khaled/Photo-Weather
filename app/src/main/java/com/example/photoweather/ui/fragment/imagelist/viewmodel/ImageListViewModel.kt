package com.example.photoweather.ui.fragment.imagelist.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.photoweather.core.bases.BaseViewModel
import com.example.photoweather.core.entitie.PhotoWeatherData
import com.example.photoweather.core.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val useCase: WeatherRepository
) : BaseViewModel() {

    val screenState by lazy { MutableLiveData<ImageListScreenState>() }

    fun fetchPhotoWeatherDataList() {
        screenState.postValue(ImageListScreenState.Loading)
        useCase.fetchPhotoWeatherDataList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                screenState.postValue(ImageListScreenState.Success(it))
            }, {
                screenState.postValue(ImageListScreenState.Error(it.message ?: ""))
            })
            .also { addDisposable(it) }
    }
}

sealed class ImageListScreenState {
    object Loading : ImageListScreenState()
    data class Success(val photoWeatherDataList: List<PhotoWeatherData>) : ImageListScreenState()
    data class Error(val errorMessage: String) : ImageListScreenState()
}

