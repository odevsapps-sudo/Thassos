// LanguageUtils.kt
package com.odevs.thassosscooterandatwfinder.util

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.*

object LanguageUtils {
    fun wrapContext(base: Context, lang: String): ContextWrapper {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration(base.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        val newContext = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            base.createConfigurationContext(config)
        } else {
            base.resources.updateConfiguration(config, base.resources.displayMetrics)
            base
        }

        return ContextWrapper(newContext)
    }
}
