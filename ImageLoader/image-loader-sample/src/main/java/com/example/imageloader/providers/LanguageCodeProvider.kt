package com.example.imageloader.providers

interface LanguageCodeProvider {
    fun getLanguageCode(): String
}

class LanguageCodeProviderImpl : LanguageCodeProvider {
    override fun getLanguageCode() = "ES"
}
