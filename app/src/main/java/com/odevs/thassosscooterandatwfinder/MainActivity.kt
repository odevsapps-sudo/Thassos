package com.odevs.thassosscooterandatwfinder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.content.ContextCompat.startActivity
import com.odevs.thassosscooterandatwfinder.localization.*
import com.odevs.thassosscooterandatwfinder.navigation.NavigationGraph
import com.odevs.thassosscooterandatwfinder.ui.theme.MyAppTheme
import com.odevs.thassosscooterandatwfinder.util.LanguageManager
import java.util.Locale

class MainActivity : ComponentActivity() {

    // 💡 Itt alkalmazzuk a nyelvi környezetet még a context csatlakozásakor
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageManager.wrapContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppTheme {
                val lang = LanguageManager.getSelectedLanguage(this)
                val strings = when (lang) {
                    "hu" -> huStrings
                    "bg" -> bgStrings
                    "en" -> enStrings
                    "ro" -> roStrings
                    else -> enStrings
                }

                CompositionLocalProvider(LocalStrings provides strings) {
                    NavigationGraph()
                }
            }
        }
    }

    // 💡 Újraindítás nyelvváltás után
    fun restartActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
