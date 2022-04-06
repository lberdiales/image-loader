package com.example.imageloader.models

import com.bumptech.glide.load.Key
import java.security.MessageDigest

data class OnDemandResource(
    val resourceName: String,
    val useCountryCode: Boolean,
    val useLanguageCode: Boolean
) : Resource() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(toString().toByteArray(Key.CHARSET))
    }
}
