package com.example.photoweather.ui.fragment.imagelist.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.photoweather.R
import com.example.photoweather.core.PHOTO_WEATHER_DATA
import com.example.photoweather.core.entitie.PhotoWeatherData
import com.example.photoweather.databinding.FragmentPhotoWeatherListBinding
import com.example.photoweather.ui.fragment.imagelist.view.adapter.PhotoWeatherListAdapter
import com.example.photoweather.ui.fragment.imagelist.viewmodel.ImageListScreenState
import com.example.photoweather.ui.fragment.imagelist.viewmodel.ImageListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ImageListFragment : Fragment() {

    private var _binding: FragmentPhotoWeatherListBinding? = null
    private val binding get() = _binding!!

    private val imageListViewModel by viewModels<ImageListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoWeatherListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    private fun initObserve() {
        imageListViewModel.screenState.observe(viewLifecycleOwner, ::onScreenStateChange)
        imageListViewModel.fetchPhotoWeatherDataList()
    }

    private fun onScreenStateChange(screenState: ImageListScreenState) {
        when (screenState) {
            ImageListScreenState.Loading -> showLoader()
            is ImageListScreenState.Success -> successHandle(screenState.photoWeatherDataList)
            is ImageListScreenState.Error -> errorHandle(screenState.errorMessage)
        }
    }

    private fun showLoader() {
        binding.progressBar.visibility = VISIBLE
    }

    private fun hideLoader() {
        binding.progressBar.visibility = GONE
    }

    private fun successHandle(photoWeatherDataList: List<PhotoWeatherData>) {
        hideLoader()
        bindPhotoWeatherList(photoWeatherDataList)
    }

    private fun bindPhotoWeatherList(photoWeatherDataList: List<PhotoWeatherData>) {
        if (photoWeatherDataList.isEmpty()){
            binding.textEmpty.visibility = VISIBLE
        }else{
            binding.textEmpty.visibility = GONE
            binding.recyclerPhotoWeather.adapter =
                PhotoWeatherListAdapter(photoWeatherDataList, ::onPhotoWeatherClicked)
        }
    }

    private fun onPhotoWeatherClicked(photoWeatherData: PhotoWeatherData) {
        findNavController().navigate(
            R.id.action_navigation_notifications_to_showPhotoWeatherFragment,
            Bundle().apply {
                putParcelable(
                    PHOTO_WEATHER_DATA, photoWeatherData
                )
            }
        )
    }

    private fun errorHandle(errorMessage: String) {
        hideLoader()
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        Log.v("MyErorr", errorMessage)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}