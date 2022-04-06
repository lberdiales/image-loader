package com.example.imageloader.loaders

import android.util.Log
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.example.imageloader.models.OnDemandResource
import com.example.imageloader.models.OnDemandResourceResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class OnDemandResourceApiFetcher(
    private val client: Call.Factory,
    private val onDemandResource: OnDemandResource
) : Callback {

    private var responseBody: ResponseBody? = null
    private var callback: DataFetcher.DataCallback<in GlideUrl>? = null

    @Volatile
    private var call: Call? = null

    fun loadData(callback: DataFetcher.DataCallback<in GlideUrl>) {
        val requestBuilder = Request.Builder().get().url(onDemandResource.url)
        // url.headers.forEach { (key, value) ->
        //     requestBuilder.addHeader(key, value)
        // }
        val request = requestBuilder.build()
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
