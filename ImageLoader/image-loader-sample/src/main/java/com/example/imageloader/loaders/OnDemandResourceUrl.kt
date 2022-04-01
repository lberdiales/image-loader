package com.example.imageloader.loaders

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.Headers

data class OnDemandResourceUrl(
    val resourceName: String,
    val useCountryCode: Boolean,
    val useLanguageCode: Boolean
) : GlideUrl(buildUrl(resourceName, useCountryCode, useLanguageCode), Headers.DEFAULT) {

    companion object {
        private fun buildUrl(
            resourceName: String,
            useCountryCode: Boolean,
            useLanguageCode: Boolean
        ): String {
            return resourceName
        }
    }
}
