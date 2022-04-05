package com.example.imageloader.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class OnDemandResourceResponse(
    @SerializedName("url") val url: String
) {
    companion object {
        fun getSourceUrl(json: String): String {
            return Gson().fromJson(json, OnDemandResourceResponse::class.java).url
            //return "https://www.androidhire.com/wp-content/uploads/2020/03/create-library-for-android.png"
        }
    }
}
