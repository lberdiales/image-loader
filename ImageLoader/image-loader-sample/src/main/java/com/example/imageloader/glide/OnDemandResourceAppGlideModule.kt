package com.example.imageloader.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.module.AppGlideModule
import com.example.imageloader.loaders.OnDemandResourceModelLoader
import com.example.imageloader.loaders.OnDemandResourceUrl
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule
class OnDemandResourceAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(OnDemandResourceUrl::class.java, InputStream::class.java, OnDemandResourceModelLoader.Factory(OkHttpClient()))
        // registry.replace(OnDemandResourceUrl::class.java, InputStream::class.java, OnDemandResourceModelLoader.Factory(OkHttpClient()))
        // registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(OkHttpClient()))
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
