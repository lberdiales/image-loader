package com.example.imageloader

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.LottieDrawable.INFINITE
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.imageloader.loaders.OnDemandResource
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class ImageLoaderImpl : ImageLoader {
    override fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(ShimmerUtils.shimmerDrawable())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("ImageLoaderImpl", "::loadImage.onLoadFailed: $e")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("ImageLoaderImpl", "::loadImage.onResourceReady")
                    return false
                }
            })
            .into(imageView)
    }

    override fun loadOnDemandImage(imageView: ImageView, onDemandResource: OnDemandResource) {
        Glide.with(imageView.context)
            .load(onDemandResource)
            .placeholder(ShimmerUtils.shimmerDrawable())
            .into(imageView)
    }

    override fun loadLottie(imageView: LottieAnimationView, lottieUrl: String) {
        Glide.with(imageView.context)
            .`as`(LottieDrawable::class.java)
            .load(lottieUrl)
            .placeholder(ShimmerUtils.shimmerDrawable())
            .into(object : CustomViewTarget<LottieAnimationView, LottieDrawable>(imageView) {
                override fun onResourceReady(
                    resource: LottieDrawable,
                    transition: Transition<in LottieDrawable>?
                ) {
                    view.setComposition(resource.composition)
                    view.playAnimation()
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    view.setImageDrawable(errorDrawable)
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                    view.setImageDrawable(placeholder)
                }
            })
    }
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
