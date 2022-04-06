package com.example.imageloader.models

import com.bumptech.glide.load.Key
import java.security.MessageDigest

data class RemoteResource(
    val url: String
) : Resource() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(toString().toByteArray(Key.CHARSET))
    }
}
