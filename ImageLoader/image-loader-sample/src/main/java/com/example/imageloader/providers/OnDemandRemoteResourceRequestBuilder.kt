package com.example.imageloader.providers

import com.example.imageloader.models.OnDemandRemoteResource
import okhttp3.Request

interface OnDemandRemoteResourceRequestBuilder {
    fun buildRequest(onDemandRemoteResource: OnDemandRemoteResource): Request
}

class OnDemandRemoteResourceRequestBuilderImpl(
    private val countryCodeProvider: CountryCodeProvider,
    private val languageCodeProvider: LanguageCodeProvider
) : OnDemandRemoteResourceRequestBuilder {

    override fun buildRequest(onDemandRemoteResource: OnDemandRemoteResource): Request {
        return Request.Builder()
            .apply { addHeader(COUNTRY_HEADER, countryCodeProvider.getCountryCode().takeIf { onDemandRemoteResource.useCountryCode } ?: "ALL") }
            .apply { addHeader(LANGUAGE_HEADER, languageCodeProvider.getLanguageCode().takeIf { onDemandRemoteResource.useLanguageCode } ?: "ALL")}
            .url(SERVICE.format(onDemandRemoteResource.resourceName))
            .build()
    }

    companion object {
        private const val SERVICE = "https://services.grability.rappi.com/api/ms/core-lottie/name/%1s"
        private const val COUNTRY_HEADER = "country"
        private const val LANGUAGE_HEADER = "Accept-Language"
    }
}
