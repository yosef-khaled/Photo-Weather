package com.example.photoweather.ui.fragment.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.photoweather.core.entitie.PhotoWeatherData
import com.example.photoweather.core.extantions.convertImageToBase64
import com.example.photoweather.core.extantions.getImageUri
import com.example.photoweather.core.extantions.share
import com.example.photoweather.core.extantions.viewToBitmap
import com.example.photoweather.core.location_id
import com.example.photoweather.core.pic_id
import com.example.photoweather.databinding.FragmentCameraBinding
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null

    private val binding get() = _binding!!

    private val cameraViewModel by viewModels<CameraViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission(Manifest.permission.CAMERA, pic_id)
        initClicing()
        initObserve()
    }

    private fun initObserve() {
        cameraViewModel.screenState.observe(viewLifecycleOwner, ::onScreenStateChange)
    }

    private fun initClicing() {
        binding.floatingButtonCamera.setOnClickListener {
            checkPermission(Manifest.permission.CAMERA, pic_id)
        }

        binding.shareButton.setOnClickListener {
            binding.constraintPhotoWeather.viewToBitmap().getImageUri(requireContext()).share(this)
        }
    }

    private fun openCamera(permission: String = ACTION_IMAGE_CAPTURE, requestCode: Int = pic_id) {
        val camera_intent = Intent(permission)
        startActivityForResult(camera_intent, requestCode)
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                context ?: requireContext(),
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissions(arrayOf(permission), requestCode)
        } else {
            permissionGrantedLogic(requestCode)
        }
    }

    private fun permissionGrantedLogic(requestCode: Int) {
        if (requestCode == pic_id) {
            openCamera()
        } else {
            getCurrentLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == pic_id) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGrantedLogic(requestCode)
            } else {
                Toast.makeText(context, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == location_id) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGrantedLogic(requestCode)
            } else {
                Toast.makeText(context, "Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pic_id) {
            val photo = data?.extras?.get("data") as Bitmap?
            photo?.let {
                binding.imagePreview.setImageBitmap(photo)
                cameraViewModel.photo = photo
                checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, location_id)
            }
        }
    }

    private fun getCurrentLocation(
        photoBase64: String = cameraViewModel.photo.convertImageToBase64() ?: ""
    ) {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity ?: return)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location ->
                cameraViewModel.fetchWeatherData(photoBase64, location.latitude, location.longitude)
            }
    }

    private fun onScreenStateChange(screenState: CameraScreenState) {
        when (screenState) {
            CameraScreenState.Loading -> showLoader()
            is CameraScreenState.Success -> successHandle(screenState.photoWeatherData)
            is CameraScreenState.Error -> errorHandle(screenState.errorMessage)
        }
    }

    private fun showLoader() {
        binding.progressBar.visibility = VISIBLE
    }

    private fun hideLoader() {
        binding.progressBar.visibility = GONE
    }

    private fun successHandle(photoWeatherDataList: PhotoWeatherData) {
        hideLoader()
        bindPhotoWeather(photoWeatherDataList)
    }

    private fun bindPhotoWeather(photoWeatherData: PhotoWeatherData) {
        with(binding) {
            textViewTamp.text = "current :${photoWeatherData.temp}"
            textViewMaxTamp.text = "Max :$${photoWeatherData.tempMin}"
            textViewMinTamp.text = "Min :$${photoWeatherData.tempMax}"
            textViewName.text = "Name :$${photoWeatherData.locationName}"
        }
    }

    private fun errorHandle(errorMessage: String) {
        hideLoader()
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}