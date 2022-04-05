package com.example.imageloader.lottie

import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import java.io.InputStream

class LottieDecoder : ResourceDecoder<InputStream, LottieComposition> {

    override fun handles(source: InputStream, options: Options): Boolean {
        return true
    }

    override fun decode(
        source: InputStream,
        width: Int,
        height: Int,
        options: Options
    ): Resource<LottieComposition>? {
        val result = LottieCompositionFactory.fromJsonInputStreamSync(source, null)
        return result.value?.let(::SimpleResource)
    }
}
