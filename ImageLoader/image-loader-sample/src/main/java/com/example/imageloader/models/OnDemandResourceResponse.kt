package com.example.imageloader.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class OnDemandResourceResponse(
    @SerializedName("url") val url: String
) {
    companion object {
        fun getSourceUrl(json: String): String {
            // return Gson().fromJson(json, OnDemandResourceResponse::class.java).url
            return "https://blog.logrocket.com/wp-content/uploads/2021/12/glide-tutorial-create-android-photo-app.png"
        }
    }
}