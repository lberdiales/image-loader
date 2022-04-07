package com.example.imageloader.imageloaderlib.loaders

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.example.imageloader.imageloaderlib.models.OnDemandRemoteResource
import com.example.imageloader.imageloaderlib.providers.OnDemandRemoteResourceRequestBuilder
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

class OnDemandRemoteResourceModelLoader(
    private val client: Call.Factory,
    private val onDemandRemoteResourceRequestBuilder: OnDemandRemoteResourceRequestBuilder
) : ModelLoader<OnDemandRemoteResource, InputStream> {

    override fun buildLoadData(
        model: OnDemandRemoteResource,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream> {
        return ModelLoader.LoadData(
            model,
            OnDemandRemoteResourceStreamFetcher(
                onDemandRemoteResource = model,
                client = client,
                onDemandRemoteResourceRequestBuilder = onDemandRemoteResourceRequestBuilder
            )
        )
    }

    override fun handles(model: OnDemandRemoteResource): Boolean {
        return true
    }

    class Factory(
        private val client: OkHttpClient,
        private val onDemandRemoteResourceRequestBuilder: OnDemandRemoteResourceRequestBuilder
    ) : ModelLoaderFactory<OnDemandRemoteResource, InputStream> {

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<OnDemandRemoteResource, InputStream> {
            return OnDemandRemoteResourceModelLoader(client, onDemandRemoteResourceRequestBuilder)
        }

        override fun teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }
}
