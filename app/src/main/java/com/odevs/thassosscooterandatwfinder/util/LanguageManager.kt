package com.odevs.thassosscooterandatwfinder.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageManager {
    private const val LANGUAGE_PREF_KEY = "selected_language"
    private const val DEFAULT_LANGUAGE = "en"

    fun setAppLanguage(context: Context, languageCode: String) {
        val prefs = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        prefs.edit().putString(LANGUAGE_PREF_KEY, languageCode).apply()
        // A context nem frissül itt azonnal, a változás újraindításnál érvényesül
    }

    fun getSelectedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        return prefs.getString(LANGUAGE_PREF_KEY, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
    }

    fun wrapContext(context: Context): Context {
        val locale = Locale(getSelectedLanguage(context))
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }
    fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}
