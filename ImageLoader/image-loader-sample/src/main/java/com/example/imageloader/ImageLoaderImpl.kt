package com.example.imageloader

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.imageloader.models.OnDemandRemoteResource
import com.example.imageloader.models.RemoteResource

class ImageLoaderImpl : ImageLoader {

    override fun loadImage(
        resource: RemoteResource,
        targetView: ImageView,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?,
        onResourceLoaded: ((Drawable?) -> Unit)?,
        onResourceLoadFailed: ((Exception?) -> Unit)?
    ) {
        Glide.with(targetView.context)
            .load(resource.url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .addListener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onResourceLoaded?.invoke(resource)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onResourceLoadFailed?.invoke(e)
                    return false
                }
            })
            .into(targetView)
    }

    override fun loadAnimation(
        resource: RemoteResource,
        targetView: LottieAnimationView,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?,
        onResourceLoaded: ((LottieComposition?) -> Unit)?,
        onResourceLoadFailed: ((Exception?) -> Unit)?
    ) {
        Glide.with(targetView.context)
            .`as`(LottieDrawable::class.java)
            .load(resource.url)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .addListener(object : RequestListener<LottieDrawable> {
                override fun onResourceReady(
                    resource: LottieDrawable?,
                    model: Any?,
                    target: Target<LottieDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onResourceLoaded?.invoke(resource?.composition)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<LottieDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onResourceLoadFailed?.invoke(e)
                    return false
                }
            })
            .into(object : CustomViewTarget<LottieAnimationView, LottieDrawable>(targetView) {
                override fun onResourceReady(
                    resource: LottieDrawable,
                    transition: Transition<in LottieDrawable>?
                ) {
                    view.setComposition(resource.composition)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    view.setImageDrawable(errorDrawable)
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                    view.setImageDrawable(placeholder)
                }
            })
    }

    override fun loadImage(
        resource: OnDemandRemoteResource,
        targetView: ImageView,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?,
        onResourceLoaded: ((Drawable?) -> Unit)?,
        onResourceLoadFailed: ((Exception?) -> Unit)?
    ) {
        Glide.with(targetView.context)
            .load(resource)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .addListener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onResourceLoaded?.invoke(resource)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onResourceLoadFailed?.invoke(e)
                    return false
                }
            })
            .into(targetView)
    }

    override fun loadAnimation(
        resource: OnDemandRemoteResource,
        targetView: LottieAnimationView,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?,
        onResourceLoaded: ((LottieComposition?) -> Unit)?,
        onResourceLoadFailed: ((Exception?) -> Unit)?
    ) {
        Glide.with(targetView.context)
            .`as`(LottieDrawable::class.java)
            .load(resource)
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .addListener(object : RequestListener<LottieDrawable> {
                override fun onResourceReady(
                    resource: LottieDrawable?,
                    model: Any?,
                    target: Target<LottieDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onResourceLoaded?.invoke(resource?.composition)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<LottieDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onResourceLoadFailed?.invoke(e)
                    return false
                }
            })
            .into(object : CustomViewTarget<LottieAnimationView, LottieDrawable>(targetView) {
                override fun onResourceReady(
                    resource: LottieDrawable,
                    transition: Transition<in LottieDrawable>?
                ) {
                    view.setComposition(resource.composition)
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
