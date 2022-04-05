package com.example.imageloader.lottie

import android.animation.ValueAnimator
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder

class LottieDrawableTranscoder : ResourceTranscoder<LottieComposition, LottieDrawable> {

    override fun transcode(
        toTranscode: Resource<LottieComposition>,
        options: Options
    ): Resource<LottieDrawable> {
        val lottieComposition = toTranscode.get()
        val lottieDrawable = LottieDrawable().apply {
            composition = lottieComposition
            repeatCount = ValueAnimator.INFINITE
        }
        return SimpleResource(lottieDrawable)
    }
}
