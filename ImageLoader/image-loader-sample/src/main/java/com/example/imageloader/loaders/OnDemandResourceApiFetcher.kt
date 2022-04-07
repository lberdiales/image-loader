package com.example.imageloader.loaders

import android.util.Log
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.example.imageloader.models.OnDemandRemoteResource
import com.example.imageloader.models.OnDemandResourceResponse
import com.example.imageloader.providers.OnDemandRemoteResourceRequestBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class OnDemandResourceApiFetcher(
    private val onDemandRemoteResource: OnDemandRemoteResource,
    private val client: Call.Factory,
    private val onDemandRemoteResourceRequestBuilder: OnDemandRemoteResourceRequestBuilder
) : Callback {

    private var responseBody: ResponseBody? = null
    private var callback: DataFetcher.DataCallback<in GlideUrl>? = null

    @Volatile
    private var call: Call? = null

    fun loadData(callback: DataFetcher.DataCallback<in GlideUrl>) {
        val request = onDemandRemoteResourceRequestBuilder.buildRequest(onDemandRemoteResource)
        this@OnDemandResourceApiFetcher.callback = callback

        call = client.newCall(request)
        call?.enqueue(this)
    }

    override fun onResponse(call: Call, response: Response) {
        responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            val json = responseBody!!.string()
            val url = OnDemandResourceResponse.getSourceUrl(json)
            callback?.onDataReady(GlideUrl(url))
        } else callback?.onLoadFailed(HttpException(response.message(), response.code()))
    }

    override fun onFailure(call: Call, e: IOException) {
        Log.e("OnDemandResourceApiFetcher", "onFailure(call:$call, e:$e)")
        callback?.onLoadFailed(e)
    }

    fun cleanup() {
        responseBody?.close()
        callback = null
    }

    fun cancel() {
        call?.cancel()
    }
}
