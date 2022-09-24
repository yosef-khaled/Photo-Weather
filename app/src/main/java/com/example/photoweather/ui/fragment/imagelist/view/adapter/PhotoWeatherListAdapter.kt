package com.example.photoweather.ui.fragment.imagelist.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoweather.R
import com.example.photoweather.core.entitie.PhotoWeatherData
import com.example.photoweather.core.extantions.convertToBitmap

class PhotoWeatherListAdapter(
    private var photoWeatherDataList: List<PhotoWeatherData>,
    private val onPhotoClicked: (PhotoWeatherData) -> Unit
) : RecyclerView.Adapter<PhotoWeatherListAdapter.AreaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder =
        parent.context
            .let { LayoutInflater.from(it) }
            .inflate(R.layout.item_photo_weather, parent, false)
            .let { AreaViewHolder(it) }

    override fun getItemCount(): Int = photoWeatherDataList.size

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        val itemData = photoWeatherDataList[position]
        with(holder) {
            photo.setImageBitmap(itemData.ImageBase64?.convertToBitmap())
            locationName.text = itemData.locationName
            temp.text = itemData.temp.toString()
            maxTemp.text = itemData.tempMax.toString()
            minTemp.text = itemData.tempMin.toString()
            itemView.setOnClickListener {
                onPhotoClicked(itemData)
            }
        }
    }

    class AreaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo : ImageView = view.findViewById(R.id.image_item_photo)
        val locationName : TextView = view.findViewById(R.id.text_view_item_name)
        val temp : TextView = view.findViewById(R.id.text_view_item_tamp)
        val maxTemp : TextView = view.findViewById(R.id.text_view_item_max_tamp)
        val minTemp : TextView = view.findViewById(R.id.text_view_item_min_tamp)
    }
}