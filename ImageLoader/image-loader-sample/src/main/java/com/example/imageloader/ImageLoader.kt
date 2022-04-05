package com.example.imageloader

import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.example.imageloader.loaders.OnDemandResource

interface ImageLoader {
    fun loadImage(imageView: ImageView, url: String)
    fun loadOnDemandImage(imageView: ImageView, onDemandResource: OnDemandResource)
    fun loadLottie(imageView: LottieAnimationView, lottieUrl: String)
}
