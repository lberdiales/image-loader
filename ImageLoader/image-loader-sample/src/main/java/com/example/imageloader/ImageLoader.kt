package com.example.imageloader

import android.widget.ImageView
import com.example.imageloader.loaders.OnDemandResourceUrl

interface ImageLoader {
    fun loadImage(imageView: ImageView, url: String)
    fun loadOnDemandImage(imageView: ImageView, onDemandResourceUrl: OnDemandResourceUrl)
}
