package com.odevs.thassosscooterandatwfinder.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.odevs.thassosscooterandatwfinder.util.LanguageManager
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val activity = context as Activity
    var selectedLanguage by remember { mutableStateOf(LanguageManager.getSelectedLanguage(context)) }

    val availableLanguages = listOf("en", "hu", "bg", "ro")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select Language", style = MaterialTheme.typography.headlineSmall)

        availableLanguages.forEach { langCode ->
            val languageName = when (langCode) {
                "en" -> "English"
                "hu" -> "Magyar"
                "bg" -> "Български"
                "ro" -> "Română"
                else -> langCode
            }

            val isSelected = selectedLanguage == langCode
            val backgroundColor = if (isSelected) Color(0xFFB2E2B2) else Color.LightGray
            val contentColor = if (isSelected) Color.Black else Color.DarkGray

            Button(
                onClick = {
                    LanguageManager.setAppLanguage(context, langCode)
                    selectedLanguage = langCode
                    activity.recreate()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = contentColor
                ),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(languageName)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = when (selectedLanguage) {
                "hu" -> "Az alkalmazás anonim módon gyűjti a járművek iránti érdeklődéseket statisztikai célból."
                "bg" -> "Приложението анонимно събира статистика за интереса към превозните средства."
                "ro" -> "Aplicația colectează anonim statistici despre interesul pentru vehicule."
                else -> "The app anonymously collects statistics about interest in vehicles."
            },
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val contactText = when (selectedLanguage) {
            "hu" -> "Kapcsolat: odevs.apps@gmail.com"
            "bg" -> "Контакт: odevs.apps@gmail.com"
            "ro" -> "Contact: odevs.apps@gmail.com"
            else -> "Contact: odevs.apps@gmail.com"
        }

        Text(
            text = contactText,
            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF2E86DE)),
            modifier = Modifier
                .clickable {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:odevs.apps@gmail.com")
                    }
                    context.startActivity(Intent.createChooser(intent, "Send Email"))
                }
                .padding(horizontal = 12.dp)
        )
    }
}
