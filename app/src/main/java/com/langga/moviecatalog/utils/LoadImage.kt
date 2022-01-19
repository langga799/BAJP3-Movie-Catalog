package com.langga.moviecatalog.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.langga.moviecatalog.R

object LoadImage {
    fun Context.loadImage(url: String?, imageView: ImageView) {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                .error(R.drawable.ic_error))
            .into(imageView)
    }
}