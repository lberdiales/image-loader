package com.example.imageloader.models

import com.bumptech.glide.load.Key
import com.bumptech.glide.load.Key.CHARSET
import java.security.MessageDigest

data class OnDemandResource(
    val resourceName: String,
    val useCountryCode: Boolean,
    val useLanguageCode: Boolean
) : Resource() {

    val url: String
        get() = "https://services.grability.rappi.com/api/ms/core-lottie/name/$resourceName"

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(toString().toByteArray(Key.CHARSET))
    }
}
