package com.example.photoweather.ui.fragment.showphotoweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.photoweather.core.PHOTO_WEATHER_DATA
import com.example.photoweather.core.entitie.PhotoWeatherData
import com.example.photoweather.core.extantions.convertToBitmap
import com.example.photoweather.core.extantions.getImageUri
import com.example.photoweather.core.extantions.share
import com.example.photoweather.core.extantions.viewToBitmap
import com.example.photoweather.databinding.FragmentShowPhotoWeatherBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShowPhotoWeatherFragment : Fragment() {

    private var photoWeatherData: PhotoWeatherData? = null
    private var _binding: FragmentShowPhotoWeatherBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photoWeatherData = it.getParcelable(PHOTO_WEATHER_DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowPhotoWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicking()
        photoWeatherData?.let {
            bindPhotoWeather(it)
        }
    }

    private fun initClicking() {
        binding.shareButton.setOnClickListener {
            binding.constraintPhotoWeather.viewToBitmap().getImageUri(requireContext()).share(this)
        }
    }

    private fun bindPhotoWeather(photoWeatherData: PhotoWeatherData) {
        with(binding){
            textViewTamp.text = "current :${photoWeatherData.temp}"
            textViewMaxTamp.text = "Max :$${photoWeatherData.tempMin}"
            textViewMinTamp.text = "Min :$${photoWeatherData.tempMax}"
            textViewName.text = "Name :$${photoWeatherData.locationName}"
            imagePreview.setImageBitmap(photoWeatherData.ImageBase64?.takeIf { it.isNotEmpty() }?.convertToBitmap())
        }
    }

}
