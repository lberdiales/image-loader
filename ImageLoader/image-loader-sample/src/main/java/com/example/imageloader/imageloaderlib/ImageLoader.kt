package com.example.imageloader.imageloaderlib

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.example.imageloader.R
import com.example.imageloader.imageloaderlib.models.OnDemandRemoteResource
import com.example.imageloader.imageloaderlib.models.RemoteResource
import com.example.imageloader.imageloaderlib.skeletons.ShimmerUtils

interface ImageLoader {
    fun loadImage(
        resource: RemoteResource,
        targetView: ImageView,
        placeholderDrawable: Drawable? = ShimmerUtils.shimmerDrawable(),
        errorDrawable: Drawable? = ContextCompat.getDrawable(targetView.context,
            R.drawable.ic_broken_image_24dp
        ),
        onResourceLoaded: ((Drawable?) -> Unit)? = null,
        onResourceLoadFailed: ((Exception?) -> Unit)? = null
    )

    fun loadAnimation(
        resource: RemoteResource,
        targetView: LottieAnimationView,
        placeholderDrawable: Drawable? = ShimmerUtils.shimmerDrawable(),
        errorDrawable: Drawable? = ContextCompat.getDrawable(targetView.context,
            R.drawable.ic_broken_image_24dp
        ),
        onResourceLoaded: ((LottieComposition?) -> Unit)? = null,
        onResourceLoadFailed: ((Exception?) -> Unit)? = null
    )

    fun loadImage(
        resource: OnDemandRemoteResource,
        targetView: ImageView,
        placeholderDrawable: Drawable? = ShimmerUtils.shimmerDrawable(),
        errorDrawable: Drawable? = ContextCompat.getDrawable(targetView.context,
            R.drawable.ic_broken_image_24dp
        ),
        onResourceLoaded: ((Drawable?) -> Unit)? = null,
        onResourceLoadFailed: ((Exception?) -> Unit)? = null
    )

    fun loadAnimation(
        resource: OnDemandRemoteResource,
        targetView: LottieAnimationView,
        placeholderDrawable: Drawable? = ShimmerUtils.shimmerDrawable(),
        errorDrawable: Drawable? = ContextCompat.getDrawable(targetView.context,
            R.drawable.ic_broken_image_24dp
        ),
        onResourceLoaded: ((LottieComposition?) -> Unit)? = null,
        onResourceLoadFailed: ((Exception?) -> Unit)? = null
    )
}
