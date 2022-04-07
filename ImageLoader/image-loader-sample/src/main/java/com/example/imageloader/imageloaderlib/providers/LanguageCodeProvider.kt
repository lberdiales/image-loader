package com.example.imageloader.imageloaderlib.providers

interface LanguageCodeProvider {
    fun getLanguageCode(): String
}

// El language deberíamos poder obtenerlo directamente desde Locale.getDefault()
class LanguageCodeProviderImpl : LanguageCodeProvider {
    override fun getLanguageCode() = "ES"
}
