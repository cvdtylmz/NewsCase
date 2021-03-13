package com.cvdtylmz.newscase.common

import android.widget.ImageView
import com.cvdtylmz.newscase.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

fun Date.dateFormat(): String {
    return SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(this)
}