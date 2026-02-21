package com.odevs.thassosscooterandatwfinder.util

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.*

class LocalizedContextWrapper(base: Context) : ContextWrapper(base) {
    companion object {
        fun wrap(context: Context, languageCode: String): Context {
            val config = Configuration(context.resources.configuration)
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            config.setLocale(locale)

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.createConfigurationContext(config)
            } else {
                context.resources.updateConfiguration(config, context.resources.displayMetrics)
                context
            }
        }
    }
}
