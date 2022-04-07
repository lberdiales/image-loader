package com.example.imageloader.imageloaderlib.skeletons

import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

object ShimmerUtils {

    fun shimmerDrawable(
        baseAlpha: Float = 1f,
        highlightAlpha: Float = 0.9f,
        widthRatio: Float = 1.6f,
        duration: Long = 650L,
        repeatDelay: Long = 100,
        tilt: Float = 30f,
    ): ShimmerDrawable {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setBaseAlpha(baseAlpha)
            .setWidthRatio(widthRatio)
            .setHighlightAlpha(highlightAlpha)
            .setDuration(duration)
            .setRepeatDelay(repeatDelay)
            .setTilt(tilt)
            .setAutoStart(true)
            .build()
        val shimmerDrawable = ShimmerDrawable()
        shimmerDrawable.setShimmer(shimmer)
        return shimmerDrawable
    }
}
