package com.example.imageloader.loaders

import android.util.Log
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import com.example.imageloader.models.OnDemandRemoteResource
import com.example.imageloader.providers.OnDemandRemoteResourceRequestBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream

class OnDemandRemoteResourceStreamFetcher(
    private val onDemandRemoteResource: OnDemandRemoteResource,
    private val client: Call.Factory,
    private val onDemandRemoteResourceRequestBuilder: OnDemandRemoteResourceRequestBuilder
) : DataFetcher<InputStream>, Callback {

    private var onDemandRemoteResourceApiFetcher: OnDemandRemoteResourceApiFetcher? = null

    private var responseBody: ResponseBody? = null
    private var callback: DataFetcher.DataCallback<in InputStream>? = null

    private var stream: InputStream? = null

    @Volatile
    private var call: Call? = null

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        onDemandRemoteResourceApiFetcher = OnDemandRemoteResourceApiFetcher(onDemandRemoteResource, client, onDemandRemoteResourceRequestBuilder)
        onDemandRemoteResourceApiFetcher?.loadData(object : DataFetcher.DataCallback<GlideUrl> {
            override fun onDataReady(data: GlideUrl?) {
                val requestBuilder = Request.Builder().get().url(data!!.toStringUrl())
                val request = requestBuilder.build()
                this@OnDemandRemoteResourceStreamFetcher.callback = callback

                call = client.newCall(request)
                call?.enqueue(this@OnDemandRemoteResourceStreamFetcher)
            }

            override fun onLoadFailed(e: Exception) {
                callback.onLoadFailed(e)
            }
        })
    }

    override fun onResponse(call: Call, response: Response) {
        responseBody = response.body()
        if (response.isSuccessful) {
            val contentLength = responseBody!!.contentLength()
            stream = ContentLengthInputStream.obtain(responseBody!!.byteStream(), contentLength)
            callback?.onDataReady(stream)
        } else callback?.onLoadFailed(HttpException(response.message(), response.code()))
    }

    override fun onFailure(call: Call, e: IOException) {
        Log.e("OnDemandResourceStreamFetcher", "onFailure(call:$call, e:$e)")
        callback?.onLoadFailed(e)
    }

    override fun cleanup() {
        onDemandRemoteResourceApiFetcher?.cleanup()
        stream?.close()
        stream = null

        responseBody?.close()
        responseBody = null

        callback = null
    }

    override fun cancel() {
        onDemandRemoteResourceApiFetcher?.cancel()

        call?.cancel()
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }
}