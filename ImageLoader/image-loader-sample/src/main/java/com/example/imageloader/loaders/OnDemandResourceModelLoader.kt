package com.example.imageloader.loaders

import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

class OnDemandResourceModelLoader(
    private val client: Call.Factory
) : ModelLoader<OnDemandResourceUrl, InputStream> {

    override fun buildLoadData(
        model: OnDemandResourceUrl,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(model, OnDemandResourceStreamFetcher(client, model))
    }

    override fun handles(model: OnDemandResourceUrl): Boolean {
        return model is OnDemandResourceUrl
    }

    class Factory(
        private val client: OkHttpClient
    ) : ModelLoaderFactory<OnDemandResourceUrl, InputStream> {

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<OnDemandResourceUrl, InputStream> {
            return OnDemandResourceModelLoader(client)
        }

        override fun teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }
}
