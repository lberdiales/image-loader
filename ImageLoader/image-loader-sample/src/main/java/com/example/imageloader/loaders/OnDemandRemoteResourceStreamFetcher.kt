package com.example.imageloader.loaders

import com.bumptech.glide.Priority
import com.bumptech.glide.integration.okhttp3.OkHttpStreamFetcher
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.example.imageloader.models.OnDemandRemoteResource
import com.example.imageloader.models.api.OnDemandResourceResponse
import com.example.imageloader.providers.OnDemandRemoteResourceRequestBuilder
import okhttp3.Call
import java.io.InputStream

class OnDemandRemoteResourceStreamFetcher(
    private val onDemandRemoteResource: OnDemandRemoteResource,
    private val client: Call.Factory,
    private val onDemandRemoteResourceRequestBuilder: OnDemandRemoteResourceRequestBuilder
) : DataFetcher<InputStream> {

    private var apiFetcher1: OkHttpStreamFetcher? = null
    private var apiFetcher2: OkHttpStreamFetcher? = null

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        // FIXME Hay que manejar el caso donde apiFetcher1 no pueda crearse por X razón
        apiFetcher1 = OkHttpStreamFetcher(client, onDemandRemoteResourceRequestBuilder.buildRequest(onDemandRemoteResource))
        apiFetcher1?.loadData(priority, object : DataFetcher.DataCallback<InputStream> {
            override fun onDataReady(data: InputStream?) {
                // FIXME Hay que manejar el caso donde no se pueda decodear data en la respuesta esperada
                val response = OnDemandResourceResponse.getSourceUrl(String(data!!.readBytes()))
                // FIXME Hay que manejar el caso donde apiFetcher2 no pueda crearse por X razón
                apiFetcher2 = OkHttpStreamFetcher(client, GlideUrl(response))
                apiFetcher2?.loadData(priority, callback)
            }

            override fun onLoadFailed(e: java.lang.Exception) {
                callback.onLoadFailed(e)
            }
        })
    }

    override fun cleanup() {
        apiFetcher1?.cleanup()
        apiFetcher1 = null

        apiFetcher2?.cleanup()
        apiFetcher2 = null
    }

    override fun cancel() {
        apiFetcher1?.cancel()
        apiFetcher2?.cancel()
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }
}
