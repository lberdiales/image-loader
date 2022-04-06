package com.example.imageloader.providers

interface CountryCodeProvider {
    fun getCountryCode(): String
}

class CountryCodeProviderImpl : CountryCodeProvider {
    override fun getCountryCode() = "MX"
}
