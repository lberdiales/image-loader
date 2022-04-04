package com.example.imageloader.loaders

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

class OnDemandResourceModelLoader(
    private val client: Call.Factory
) : ModelLoader<OnDemandResource, InputStream> {

    override fun buildLoadData(
        model: OnDemandResource,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream> {
        return ModelLoader.LoadData(model, OnDemandResourceStreamFetcher(client, model))
    }

    override fun handles(model: OnDemandResource): Boolean {
        return true
    }

    class Factory(
        private val client: OkHttpClient
    ) : ModelLoaderFactory<OnDemandResource, InputStream> {

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<OnDemandResource, InputStream> {
            return OnDemandResourceModelLoader(client)
        }

        override fun teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }
}
