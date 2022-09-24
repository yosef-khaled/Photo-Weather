package com.example.photoweather.core.extantions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import java.io.ByteArrayOutputStream
import java.io.IOException


fun Bitmap?.convertImageToBase64(): String? {
    this ?: return null

    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
    val b = byteArrayOutputStream.toByteArray()
    val encImage = Base64.encodeToString(b, Base64.DEFAULT)
    try {
        byteArrayOutputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return encImage
}

fun String.convertToBitmap(): Bitmap {
    val decodedString: ByteArray = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}

fun View.viewToBitmap(): Bitmap {
    buildDrawingCache()
    val bm = drawingCache
    return bm
}

fun Bitmap.getImageUri(inContext: Context): Uri {
    val bytes = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        inContext.getContentResolver(),
        this,
        "Title",
        null
    )
    return Uri.parse(path)
}