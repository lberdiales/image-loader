package com.example.imageloader.glide

import android.content.Context
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.imageloader.loaders.OnDemandRemoteResourceModelLoader
import com.example.imageloader.lottie.LottieDecoder
import com.example.imageloader.lottie.LottieDrawableTranscoder
import com.example.imageloader.models.OnDemandRemoteResource
import com.example.imageloader.providers.CountryCodeProvider
import com.example.imageloader.providers.CountryCodeProviderImpl
import com.example.imageloader.providers.LanguageCodeProvider
import com.example.imageloader.providers.LanguageCodeProviderImpl
import com.example.imageloader.providers.OnDemandRemoteResourceRequestBuilder
import com.example.imageloader.providers.OnDemandRemoteResourceRequestBuilderImpl
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
