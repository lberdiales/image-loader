package com.example.imageloader.models

import androidx.annotation.DrawableRes
import java.security.MessageDigest

class AndroidResource(@DrawableRes val drawableRes: Int) : Resource {
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        TODO("Not yet implemented")
    }
}
