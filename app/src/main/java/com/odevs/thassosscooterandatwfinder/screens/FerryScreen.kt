package com.odevs.thassosscooterandatwfinder.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odevs.thassosscooterandatwfinder.localization.LocalStrings
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues

@Composable
fun FerryScreen() {
    val context = LocalContext.current
    val strings = LocalStrings.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)) // világoskék háttér
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = strings.ferryTitle,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            FerryRouteSection(
                title = strings.ferryRoute1Title,
                details = listOf(
                    strings.ferryRoute1Detail1,
                    strings.ferryRoute1Detail2,
                    strings.ferryRoute1Detail3
                )
            )

            FerryRouteSection(
                title = strings.ferryRoute2Title,
                details = listOf(
                    strings.ferryRoute2Detail1,
                    strings.ferryRoute2Detail2,
                    strings.ferryRoute2Detail3
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = strings.ferryInfoText,
                fontSize = 14.sp
            )

            Text(
                text = strings.ferryWebsite,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    val url = "http://www.thassos-ferries.gr/en/Itineraries.php?cat_id=1"
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            )
        }
    }
}

@Composable
fun FerryRouteSection(title: String, details: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        details.forEach {
            Text(text = it, fontSize = 14.sp)
        }
    }
}
