package com.example.photoweather.core.entitie

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PhotoWeatherData")
data class PhotoWeatherData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "ImageBase64")
    val ImageBase64: String?,


    @ColumnInfo(name = "temp")
    val temp: Double,

    @ColumnInfo(name = "tempMax")
    val tempMax: Double,

    @ColumnInfo(name = "tempMin")
    val tempMin: Double,

    @ColumnInfo(name = "locationName")
    val locationName: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(ImageBase64)
        parcel.writeDouble(temp)
        parcel.writeDouble(tempMax)
        parcel.writeDouble(tempMin)
        parcel.writeString(locationName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoWeatherData> {
        override fun createFromParcel(parcel: Parcel): PhotoWeatherData {
            return PhotoWeatherData(parcel)
        }

        override fun newArray(size: Int): Array<PhotoWeatherData?> {
            return arrayOfNulls(size)
        }
    }
}
