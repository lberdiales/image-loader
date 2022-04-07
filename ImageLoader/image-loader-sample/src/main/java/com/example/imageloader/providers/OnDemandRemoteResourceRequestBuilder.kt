package com.example.imageloader.providers

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.imageloader.models.OnDemandRemoteResource

interface OnDemandRemoteResourceRequestBuilder {
    fun buildRequest(onDemandRemoteResource: OnDemandRemoteResource): GlideUrl
}

class OnDemandRemoteResourceRequestBuilderImpl(
    private val countryCodeProvider: CountryCodeProvider,
    private val languageCodeProvider: LanguageCodeProvider
) : OnDemandRemoteResourceRequestBuilder {

    override fun buildRequest(onDemandRemoteResource: OnDemandRemoteResource): GlideUrl {
        return GlideUrl(
            SERVICE.format(onDemandRemoteResource.resourceName),
            LazyHeaders.Builder()
                .addHeader(COUNTRY_HEADER, countryCodeProvider.getCountryCode().takeIf { onDemandRemoteResource.useCountryCode } ?: "ALL")
                .addHeader(LANGUAGE_HEADER, languageCodeProvider.getLanguageCode().takeIf { onDemandRemoteResource.useLanguageCode } ?: "ALL")
                .build()
        )
    }

    companion object {
        private const val SERVICE = "https://services.grability.rappi.com/api/ms/core-lottie/name/%1s"
        private const val COUNTRY_HEADER = "country"
        private const val LANGUAGE_HEADER = "Accept-Language"
    }
}
