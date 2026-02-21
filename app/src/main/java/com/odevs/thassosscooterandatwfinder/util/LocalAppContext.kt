package com.odevs.thassosscooterandatwfinder.util

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppContext = staticCompositionLocalOf<Context> {
    error("No localized context provided")
}
