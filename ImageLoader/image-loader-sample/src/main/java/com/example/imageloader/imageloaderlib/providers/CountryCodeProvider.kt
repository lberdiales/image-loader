package com.example.imageloader.imageloaderlib.providers

interface CountryCodeProvider {
    fun getCountryCode(): String
}

// Esto simula PayCountryDataProvider
class CountryCodeProviderImpl : CountryCodeProvider {
    override fun getCountryCode() = "MX"
}
