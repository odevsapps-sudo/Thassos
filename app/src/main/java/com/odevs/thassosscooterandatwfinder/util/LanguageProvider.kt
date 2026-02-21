package com.odevs.thassosscooterandatwfinder.util

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import java.util.Locale

fun Context.wrapInLanguage(languageCode: String): Context {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    config.setLocales(LocaleList(locale))
    return createConfigurationContext(config)
}
