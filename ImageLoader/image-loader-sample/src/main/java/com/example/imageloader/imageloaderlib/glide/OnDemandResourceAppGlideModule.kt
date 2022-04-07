package com.example.imageloader.imageloaderlib.glide

import android.content.Context
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.imageloader.imageloaderlib.loaders.OnDemandRemoteResourceModelLoader
import com.example.imageloader.imageloaderlib.lottie.LottieDecoder
import com.example.imageloader.imageloaderlib.lottie.LottieDrawableTranscoder
import com.example.imageloader.imageloaderlib.models.OnDemandRemoteResource
import com.example.imageloader.imageloaderlib.providers.CountryCodeProvider
import com.example.imageloader.imageloaderlib.providers.CountryCodeProviderImpl
import com.example.imageloader.imageloaderlib.providers.LanguageCodeProvider
import com.example.imageloader.imageloaderlib.providers.LanguageCodeProviderImpl
import com.example.imageloader.imageloaderlib.providers.OnDemandRemoteResourceRequestBuilder
import com.example.imageloader.imageloaderlib.providers.OnDemandRemoteResourceRequestBuilderImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.InputStream

@GlideModule
class OnDemandResourceAppGlideModule : AppGlideModule() {

    private val countryCodeProvider: CountryCodeProvider = CountryCodeProviderImpl()
    private val languageCodeProvider: LanguageCodeProvider = LanguageCodeProviderImpl()
    // Esta instancia tiene que venir desde App, ¿Dagger?
    private val onDemandRemoteResourceRequestBuilder: OnDemandRemoteResourceRequestBuilder = OnDemandRemoteResourceRequestBuilderImpl(countryCodeProvider, languageCodeProvider)

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val okHttpClient = buildOkHttpClient()
        registry.prepend(OnDemandRemoteResource::class.java, InputStream::class.java, OnDemandRemoteResourceModelLoader.Factory(okHttpClient, onDemandRemoteResourceRequestBuilder))
        registry.prepend(InputStream::class.java, LottieComposition::class.java, LottieDecoder())
        registry.register(LottieComposition::class.java, LottieDrawable::class.java, LottieDrawableTranscoder())
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    // Esta instancia tiene que venir desde App, ¿Dagger?
    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}
