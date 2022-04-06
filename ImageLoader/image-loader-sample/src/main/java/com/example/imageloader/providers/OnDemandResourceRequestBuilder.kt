package com.example.imageloader.providers

import com.example.imageloader.models.OnDemandResource
import okhttp3.Request

interface OnDemandResourceRequestBuilder {
    fun buildRequest(onDemandResource: OnDemandResource): Request
}

class OnDemandResourceRequestBuilderImpl(
    private val countryCodeProvider: CountryCodeProvider,
    private val languageCodeProvider: LanguageCodeProvider
) : OnDemandResourceRequestBuilder {

    override fun buildRequest(onDemandResource: OnDemandResource): Request {
        return Request.Builder()
            .apply { addHeader(COUNTRY_HEADER, countryCodeProvider.getCountryCode().takeIf { onDemandResource.useCountryCode } ?: "ALL") }
            .apply { addHeader(LANGUAGE_HEADER, languageCodeProvider.getLanguageCode().takeIf { onDemandResource.useLanguageCode } ?: "ALL")}
            .url(SERVICE.format(onDemandResource.resourceName))
            .build()
    }

    companion object {
        private const val SERVICE = "https://services.grability.rappi.com/api/ms/core-lottie/name/%1s"
        private const val COUNTRY_HEADER = "country"
        private const val LANGUAGE_HEADER = "Accept-Language"
    }
}
