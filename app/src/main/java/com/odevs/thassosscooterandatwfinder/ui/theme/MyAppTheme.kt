package com.odevs.thassosscooterandatwfinder.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(), // vagy darkColorScheme()
        typography = Typography(), // vagy saját Typography definíciód
        content = content
    )
}
