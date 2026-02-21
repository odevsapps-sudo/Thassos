package com.odevs.thassosscooterandatwfinder.ui.gettingthere

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odevs.thassosscooterandatwfinder.localization.LocalStrings
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues

data class LinkItem(val text: String, val url: String)

@Composable
fun GettingThereScreen() {
    val context = LocalContext.current
    val strings = LocalStrings.current
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // fontos!
                .verticalScroll(scrollState)
                .padding(16.dp) // opcionális, ha szép margót akarsz
        ) {
            Text(
                text = strings.reachThassos,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TravelSection(
                title = "\uD83C\uDDFD Budapest → Thassos",
                description = "${strings.routeBudapest}\n\n${strings.tollsHungary}\n${strings.restBudapest}",
                links = listOf(
                    LinkItem("🇭🇺 e-matrica", "https://ematrica.nemzetiutdij.hu/"),
                    LinkItem("🇧🇬 e-matrica", "https://web.bgtoll.bg/")
                ),
                rest = strings.restBudapestTip
            )

            Spacer(modifier = Modifier.height(24.dp))

            TravelSection(
                title = "\uD83C\uDDFD Budapest → Thassos (via North Macedonia)",
                description = "${strings.routeBudapestAlt}\n\n${strings.tollsNorthMacedonia}",
                rest = strings.restBudapestAlt
            )

            Spacer(modifier = Modifier.height(24.dp))

            TravelSection(
                title = "\uD83C\uDDF7\uD83C\uDDF4 Bucharest → Thassos",
                description = "${strings.routeBucharest}\n\n${strings.tollsBulgaria}",
                linkText = strings.buyHere,
                linkUrl = "https://web.bgtoll.bg/",
                rest = strings.restBucharest
            )

            Spacer(modifier = Modifier.height(24.dp))

            TravelSection(
                title = "\uD83C\uDDE7\uD83C\uDDEC Sofia → Thassos",
                description = strings.routeSofia
            )

            Spacer(modifier = Modifier.height(32.dp)) // hogy biztos legyen alja 👇
        }
    }
}


@Composable
fun TravelSection(
    title: String,
    description: String,
    linkText: String? = null,
    linkUrl: String? = null,
    links: List<LinkItem>? = null,
    rest: String = ""
) {
    val context = LocalContext.current

    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = description,
            fontSize = 15.sp,
            lineHeight = 20.sp
        )

        links?.forEach { link ->
            Text(
                text = link.text,
                color = Color.Blue,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.url))
                        context.startActivity(intent)
                    }
            )
        }

// A fallback, ha csak egy link van (régi struktúra miatt)
        if (!linkText.isNullOrEmpty() && !linkUrl.isNullOrEmpty()) {
            Text(
                text = linkText,
                color = Color.Blue,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
                        context.startActivity(intent)
                    }
            )
        }

        if (rest.isNotEmpty()) {
            Text(
                text = rest,
                fontSize = 15.sp,
                lineHeight = 20.sp
            )
        }
    }
}
