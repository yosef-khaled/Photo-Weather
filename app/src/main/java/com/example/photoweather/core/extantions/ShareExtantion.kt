package com.example.photoweather.core.extantions

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment


fun Uri.share(fragment: Fragment) {
    fragment.sendTweet(this)
}

fun Fragment.sendTweet(uri: Uri) {
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.type = "image/jpeg"
    intent.setPackage("com.twitter.android")
    startActivity(intent)
}