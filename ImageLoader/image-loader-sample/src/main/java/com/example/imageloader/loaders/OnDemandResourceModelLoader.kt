package com.example.imageloader.loaders

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.example.imageloader.models.OnDemandResource
import com.example.imageloader.providers.OnDemandResourceRequestBuilder
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

class OnDemandResourceModelLoader(
    private val client: Call.Factory,
    private val onDemandResourceRequestBuilder: OnDemandResourceRequestBuilder
) : ModelLoader<OnDemandResource, InputStream> {

    override fun buildLoadData(
        model: OnDemandResource,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream> {
        return ModelLoader.LoadData(
            model,
            OnDemandResourceStreamFetcher(
                onDemandResource = model,
                client = client,
                onDemandResourceRequestBuilder = onDemandResourceRequestBuilder
            )
        )
    }

    override fun handles(model: OnDemandResource): Boolean {
        return true
    }

    class Factory(
        private val client: OkHttpClient,
        private val onDemandResourceRequestBuilder: OnDemandResourceRequestBuilder
    ) : ModelLoaderFactory<OnDemandResource, InputStream> {

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<OnDemandResource, InputStream> {
            return OnDemandResourceModelLoader(client, onDemandResourceRequestBuilder)
        }

        override fun teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }
}
