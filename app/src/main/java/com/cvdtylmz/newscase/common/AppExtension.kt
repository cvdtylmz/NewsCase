package com.cvdtylmz.newscase.common

import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cvdtylmz.newscase.R
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadImageUrl(url: String?, applyCircle: Boolean = false) {
    val glide = Glide.with(this).load(url ?: R.drawable.ic_launcher_foreground)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.centerCrop().into(this)
    }
}

fun ImageButton.loadImage(imagePath: Int) {
    Glide.with(this).load(imagePath).into(this)
}


fun String.dateFormat(): String {
    return this.substring(0,10)
}