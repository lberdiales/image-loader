package com.example.imageloader

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imageloader.loaders.OnDemandResourceUrl
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

    override fun loadOnDemandImage(imageView: ImageView, onDemandResourceUrl: OnDemandResourceUrl) {
        Glide.with(imageView.context)
            .load(onDemandResourceUrl)
            .placeholder(ShimmerUtils.shimmerDrawable())
            .into(imageView)
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
