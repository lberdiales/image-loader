package com.example.imageloader.glide

import android.content.Context
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.imageloader.models.OnDemandResource
import com.example.imageloader.loaders.OnDemandResourceModelLoader
import com.example.imageloader.lottie.LottieDecoder
import com.example.imageloader.lottie.LottieDrawableTranscoder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.InputStream

@GlideModule
class OnDemandResourceAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(MyInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        registry.prepend(OnDemandResource::class.java, InputStream::class.java, OnDemandResourceModelLoader.Factory(okHttpClient))
        registry.prepend(InputStream::class.java, LottieComposition::class.java, LottieDecoder())
        registry.register(LottieComposition::class.java, LottieDrawable::class.java, LottieDrawableTranscoder())
        // registry.prepend(LottieResource::class.java, InputStream::class.java, OnDemandResourceModelLoader.Factory(okHttpClient))
        // registry.replace(OnDemandResourceUrl::class.java, InputStream::class.java, OnDemandResourceModelLoader.Factory(OkHttpClient()))
        // registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(OkHttpClient()))
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder().apply {
            addHeader("Accept-Language", "ALL")
            addHeader("country", "MX")
        }

        return chain.proceed(requestBuilder.build())
    }
}