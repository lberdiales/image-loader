package com.example.imageloader

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.example.imageloader.models.OnDemandRemoteResource
import com.example.imageloader.models.RemoteResource
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

interface ImageLoader {
    fun loadImage(
        resource: RemoteResource,
        targetView: ImageView,
        placeholderDrawable: Drawable? = ShimmerUtils.shimmerDrawable(),
        errorDrawable: Drawable? = null,
        onResourceLoaded: (() -> Unit)? = null,
        onResourceLoadFailed: ((Exception?) -> Unit)? = null
    )

    fun loadAnimation(
        resource: RemoteResource,
        targetView: LottieAnimationView,
        placeholderDrawable: Drawable? = ShimmerUtils.shimmerDrawable(),
        errorDrawable: Drawable? = null,
        onResourceLoaded: (() -> Unit)? = null,
        onResourceLoadFailed: ((Exception?) -> Unit)? = null
    )

    fun loadImage(
        resource: OnDemandRemoteResource,
        targetView: ImageView,
        placeholderDrawable: Drawable? = ShimmerUtils.shimmerDrawable(),
        errorDrawable: Drawable? = null,
        onResourceLoaded: (() -> Unit)? = null,
        onResourceLoadFailed: ((Exception?) -> Unit)? = null
    )

    fun loadAnimation(
        resource: OnDemandRemoteResource,
        targetView: LottieAnimationView,
        placeholderDrawable: Drawable? = ShimmerUtils.shimmerDrawable(),
        errorDrawable: Drawable? = null,
        onResourceLoaded: (() -> Unit)? = null,
        onResourceLoadFailed: ((Exception?) -> Unit)? = null
    )
}

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
