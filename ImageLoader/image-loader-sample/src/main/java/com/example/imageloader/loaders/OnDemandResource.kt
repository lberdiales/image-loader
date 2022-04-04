package com.example.imageloader.loaders

import com.bumptech.glide.load.Key
import com.bumptech.glide.load.Key.CHARSET
import java.security.MessageDigest

data class OnDemandResource(
    val resourceName: String,
    val useCountryCode: Boolean,
    val useLanguageCode: Boolean
) : Key {

    var stringUrl: String = ""

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(toString().toByteArray(CHARSET))
    }
}
